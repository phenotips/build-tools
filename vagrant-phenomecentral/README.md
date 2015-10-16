# Running your very own PhenomeCentral.org instance

## Installation

Requires:

- [Vagrant](http://vagrantup.com/)
- [Ansible](http://ansible.com/)
- [Virtualbox](http://virtualbox.org/)

The machine must have around 50gb of disk space. (Exomiser itself takes
up 25). `setup.sh` takes care of this for you by configuring Virtualbox, but if
you're running Vagrant on another provider (not Virtualbox), or on Windows,
this task is up to you.

Run:

    $ ./setup.sh
    $ vagrant provision

## Development
