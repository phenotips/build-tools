# Phenotips Deployments

This is the suggested Phenotips Dev environment layout:

```
$YOUR_DEV_DIR
│
│
├── build-tools/
│   ├── ansible/
│   │ ....
│   └── vagrant-phenomecentral/
│
├── exomiser/
│   └── exomiser-cli-7.0.0-distribution.zip
│
├── patient-network/
│
├── phenomecentral.org/
│
└── phenotips/

```

# Usage example:

    $ ansible-playbook --ask-become-pass phenomecentral.yml -i YOUR_HOST_FILE

As a guide to setting up the hostfile, see http://docs.ansible.com/ansible/playbooks_best_practices.html

Generally, you'd have multiple host files, and customize each host in
`./host_vars/$host`.

# TODO:

- updating a running instance (see the **broken** [phenotips upgrade scritpt](https://phenotips.org/AdminGuide/Upgrade))
   - update on version change?
   - update on .zip file differences?
   - downgrade procedures?
