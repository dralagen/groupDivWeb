groupDivWeb  [![Build Status](https://travis-ci.org/dralagen/groupDivWeb.svg?branch=develop)](https://travis-ci.org/dralagen/groupDivWeb)
===========

Implementation of [groupDiv](https://github.com/dralagen/groupDiv) on web interface.

Developer
---------

To compile the project

```
mvn install -Dmaven.test.skip=true
# or
mvn clean install -Dmaven.test.skip=true
```

To launch the google app engine dev serveur

```
mvn -pl rest-api appengine:devserver
```

How To deploy
-------------

Add  profile in ~/.m2/settings.xml, all information of api key are in your [admin console](https://console.developers.google.com) in credential part

```
<settings>
    ...
    <profiles>
        <profile>
            <id>groupDivWebProd</id>
            <properties>
                <api.id>API_Client_Id</api.id>
                <api.secret>API_Client_Secret</api.secret>
            </properties>
        </profile>
    </profiles>
    ...
</settings>
```
And execute this command for recompile the project with good profile, and deploy the project
```
mvn clean install -P groupDivWebProd && mvn -pl rest-api appengine:update
```

How to release
--------------

On branch develop you can release with two command :
```
mvn jgitflow:release-start
mvn jgitflow:release-finish -DnoDeploy=true -Dgpg.skip=true
```
release-start create a new branch for create release and release-finish create tag for the new version.

To publish the release launch this command :
```
git push origin master && git push origin develop && git push --tags
```
