FROM oraclelinux:8-slim
LABEL maintainer="Dave Ray Reizes <daveray@daveray.net>"

### install some stuff we need
RUN cd /usr/bin &&\
 ln -s microdnf dnf &&\
 cd ~ &&\
 dnf install which && \
 dnf install wget && dnf install gzip &&\
 dnf install freetype fontconfig &&\ 
 dnf install unzip &&\
 dnf install findutils &&\
 dnf install sudo &&\
 dnf install util-linux &&\
 groupadd -g 500 appuser &&\
 useradd -d /home/appuser -u 500 -g appuser -G wheel,appuser -m -s /bin/bash appuser -p rewards &&\ 
# fetch the java 17 binary 
 mkdir /opt/jvm && cd /opt/jvm &&\
 wget https://download.oracle.com/java/17/archive/jdk-17.0.9_linux-x64_bin.tar.gz &&\
 tar -xvf jdk-17.0.9_linux-x64_bin.tar.gz &&\
# install gradle
 cd ~ && mkdir /opt/gradle && cd /opt/gradle/ &&\
 wget https://services.gradle.org/distributions/gradle-8.4-bin.zip &&\
 unzip gradle-8.4-bin.zip &&\
## remove the dnf cache and downloaded archives
 rm -rf /var/cache/dnf &&\
 rm -rf /opt/jvm/jdk-17.0.9_linux-x64_bin.tar.gz &&\
 rm -rf /opt/gradle/gradle-8.4-bin.zip;
 
## set up the environment 
ENV LANG=en_US.UTF-8
ENV JAVA_HOME=/opt/jvm/jdk-17.0.9
ENV GRADLE_HOME=/opt/gradle/gradle-8.4/bin/gradle
ENV PATH=$PATH:$JAVA_HOME/bin:/opt/gradle/gradle-8.4/bin
    
# setup alternatives, thanks to "Aurelio Garcia-Ribeyro <aurelio.garciaribeyro@oracle.com>"
RUN set -eux; \
 ln -sfT "$JAVA_HOME" /opt/jvm/default; \
 ln -sfT "$JAVA_HOME" /opt/jvm/latest; \
 for bin in "$JAVA_HOME/bin/"*; do \
   base="$(basename "$bin")"; \
   [ ! -e "/usr/bin/$base" ]; \
   alternatives --install "/usr/bin/$base" "$base" "$bin" 20000; \
 done;

## change to appuser
USER appuser

## download and build the application
RUN cd ~ &&\
 wget https://github.com/daveray-net/rewards-api/archive/refs/heads/main.zip &&\
 unzip main.zip &&\
 cd rewards-api-main &&\
 gradle tasks &&\
 gradle bootJar &&\
 #cleanup sources zipfile and build cache
 rm -vf ../main.zip &&\
 rm -vfRd .gradle;

## switch to runtime mode
EXPOSE 8080 

## run application as non-root user
USER appuser
WORKDIR /home/appuser/rewards-api-main
ENTRYPOINT exec java -jar build/libs/rewards-api*.jar





