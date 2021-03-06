echo 1
scp -r /home/arseniy/dev/itmo/soa/lab2-extra/target/lab2-extra-1.0-SNAPSHOT.war /home/arseniy/wildfly-21.0.1.Final/standalone/deployments
cp /home/arseniy/dev/itmo/soa/out/artifacts/ejb_archive/ejb-archive.jar /home/arseniy/wildfly-21.0.1.Final/standalone/deployments
echo 2


scp -r /home/arseniy/dev/itmo/soa/lab2-extra/target/lab2-extra-1.0-SNAPSHOT.war helios:wildfly/standalone/deployments
echo "Finished"

