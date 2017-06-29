# OPSGENIE-OSS

[![License](https://img.shields.io/github/license/opsgenie/opsgenie-oss.svg)](http://www.apache.org/licenses/LICENSE-2.0)
[![Lifecycle](https://img.shields.io/osslifecycle/opsgenie/opsgenie-oss.svg)]()

## Get OpsGenie-OSS

Binaries are available from [Maven Central](http://search.maven.org/#search%7Cga%7C1%7Ccom.opsgenie).

|Group|Artifact|Latest Stable Version|
|-----------|---------------|---------------------|
|com.opsgenie|opsgenie-*|[![Maven Central](https://img.shields.io/maven-central/v/com.opsgenie/opsgenie-oss.svg)]()|

Below are the various artifacts published:

|Artifact|Description|
|-----------|---------------|
|opsgenie-core|Core module|
|opsgenie-aws-core|Core AWS module|
|opsgenie-aws-s3|S3 extension for AWS module|

## Builds

OpsGenie-OSS builds are run on OpsGenie's internal Jenkins.

|  Branch |                                                     Build                                                     |                                                                         Coverage                                                                         |                                                                         Tests                                                                         |
|:-------:|:-------------------------------------------------------------------------------------------------------------:|:--------------------------------------------------------------------------------------------------------------------------------------------------------:|:-----------------------------------------------------------------------------------------------------------------------------------------------------:|
|  Master | [![Build Status](https://jenkins.opsgeni.us/buildStatus/buildIcon?job=OpsGenieOSSBuild)](https://jenkins.opsgeni.us/view/OpsGenie%20OSS/job/OpsGenieOSSBuild/) | [![Code Coverage](https://jenkins.opsgeni.us/buildStatus/coverageIcon?job=OpsGenieOSSBuild)](https://jenkins.opsgeni.us/view/OpsGenie%20OSS/job/OpsGenieOSSBuild/) | [![Test Status](https://jenkins.opsgeni.us/buildStatus/testIcon?job=OpsGenieOSSBuild)](https://jenkins.opsgeni.us/view/OpsGenie%20OSS/job/OpsGenieOSSBuild/) |

## Requirements

* JDK 1.8+ (for build and execution)
* Maven 3.x (for build)

## Build

To build:

```
$ git clone git@github.com:opsgenie/opsgenie-oss.git
$ cd opsgenie-oss/
$ mvn clean install
```

## Issues and Feedback

[![Issues](https://img.shields.io/github/issues/opsgenie/opsgenie-oss.svg)](https://github.com/opsgenie/opsgenie-oss/issues?q=is%3Aopen+is%3Aissue)
[![Closed issues](https://img.shields.io/github/issues-closed/opsgenie/opsgenie-oss.svg)](https://github.com/opsgenie/opsgenie-oss/issues?q=is%3Aissue+is%3Aclosed)

Please use [GitHub Issues](https://github.com/opsgenie/opsgenie-oss/issues) for any bug report, feature request and support.

## Contribution

[![Pull requests](https://img.shields.io/github/issues-pr/opsgenie/opsgenie-oss.svg)](https://github.com/opsgenie/opsgenie-oss/pulls?q=is%3Aopen+is%3Apr)
[![Closed pull requests](https://img.shields.io/github/issues-pr-closed/opsgenie/opsgenie-oss.svg)](https://github.com/opsgenie/opsgenie-oss/pulls?q=is%3Apr+is%3Aclosed)
[![Contributors](https://img.shields.io/github/contributors/opsgenie/opsgenie-oss.svg)]()

If you would like to contribute, please 
- Fork the repository on GitHub and clone your fork.
- Create a branch for your changes and make your changes on it.
- Send a pull request by explaining clearly what is your contribution.

> Tip: Please check the existing pull requests for similar contributions and consider submit an issue to discuss the proposed feature before writing code.

## LICENSE

Copyright (c) 2016 OpsGenie

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

<http://www.apache.org/licenses/LICENSE-2.0>

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
