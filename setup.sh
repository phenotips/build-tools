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
vagrant ssh -c "yum install -yq bzip2"
vagrant vbguest
vagrant halt

#########################
# Resize VM's Drive
#

pushd

VM=`VBoxManage list vms | grep phenomecentral | cut -f1 -d ' ' | tr -d '"'`
cd ~/VirtualBox VMs/$VM

DISK=`ls | grep vmdk | sed 's/.vmdk$//'`

VBoxManage clonehd "$DISK".vmdk "$DISK".vdi –format VDI

VBoxManage modifyhd "$DISK".vdi -–resize 50000
# detach vmdk
VBoxManage storageattach $VM --storagectl 'IDE Controller' --port 0 --device 0 --medium none
# detach vdi
VBoxManage storageattach $VM --storagectl 'IDE Controller' --port 0 --device 0 --medium "$DISK".vdi --type hdd
# nuke vmdk
rm -rf "$DISK".vmdk
popd

###############################################
# Resize VM's Partition to encompass new space.
#

vagrant up
# make partition in the remaining space with flag 8e
#vagrant ssh -c "

#"
#vagrant ssh -c "sudo partprobe"

#################################
# All done. Setup Phenomecentral
#

#vagrant provision
