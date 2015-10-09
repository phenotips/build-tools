#!/bin/bash
set -e
set -o pipefail

#########################
# Create VM
#

# if no virtualbox, just run vagrant up
if [ ! -x `which VBoxManage` ]; then
   vagrant up
   exit $?
fi

# The rest of this script is for VirtualBox

vagrant up --no-provision
vagrant ssh -c "sudo yum install -y -q bzip2"
vagrant vbguest
vagrant halt

#########################
# Resize VM's Drive
#

VM=`VBoxManage list vms | grep phenomecentral | cut -f1 -d ' ' | tr -d '"'`
pushd "$HOME/VirtualBox VMs/$VM"

DISK=`ls | grep vmdk | sed 's/.vmdk$//'`

echo "Cloning drive..."
VBoxManage clonemedium disk "$DISK".vmdk "$DISK".vdi --format VDI

echo "Growing new drive..."
VBoxManage modifyhd "$DISK".vdi --resize 50000

echo "Unmounting old drive..."
VBoxManage storageattach $VM --storagectl 'IDE Controller' --port 0 --device 0 --medium none

echo "Mounting new drive..."
VBoxManage storageattach $VM --storagectl 'IDE Controller' --port 0 --device 0 --medium "$DISK".vdi --type hdd

echo "Removing old drive..."
rm -rf "$DISK".vmdk
popd

###############################################
# Resize VM's Partition to encompass new space.
#

# make partition in the remaining space with flag 8e
vagrant ssh -c "sudo /home/vagrant/sync/makepart.sh"
vagrant ssh -c "sudo partprobe"
vagrant ssh -c "sudo pvcreate /dev/sda5"
vagrant ssh -c "sudo vgextend VolGroup00 /dev/sda5"
vagrant ssh -c "sudo lvextend /dev/VolGroup00/LogVol00 /dev/sda5"
vagrant ssh -c "sudo resize2fs /dev/mapper/VolGroup00-LogVol00"

