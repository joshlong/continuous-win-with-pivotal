#!/bin/bash

set -e

curl http://repo.spring.io/release/org/springframework/boot/spring-boot-cli/1.2.5.RELEASE/spring-boot-cli-1.2.5.RELEASE-bin.tar.gz  > cli.tgz

tar zxpf cli.tgz

mv spring-1.2.5.RELEASE spring-cli
cd spring-cli && echo "`pwd`"

cd ..
echo $PATH

spring --version
spring install org.springframework.cloud:spring-cloud-cli:1.0.3.RELEASE
