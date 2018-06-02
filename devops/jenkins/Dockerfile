# FROM jenkins:2.60.3-alpine
FROM jenkins/jenkins:2.101-alpine

USER root

RUN apk -U add docker
# Setup Jenkins
RUN echo "jenkins ALL=NOPASSWD: ALL" >> /etc/sudoers
RUN apk add --update shadow \
    && groupadd -g 50 staff \
    && usermod -a -G staff jenkins
USER jenkins
RUN /usr/local/bin/install-plugins.sh \
blueocean \
build-environment \
cloudbees-folder \
config-file-provider \
credentials-binding \
credentials \
docker-plugin \
docker-slaves \
envinject \
git \
greenballs \
groovy \
job-dsl \
jobConfigHistory \
naginator \
pam-auth \
pipeline-utility-steps \
nexus-artifact-uploader \
slack \
workflow-aggregator \
sonar \
subversion

ENV JAVA_OPTS="-Djenkins.install.runSetupWizard=false"
USER root