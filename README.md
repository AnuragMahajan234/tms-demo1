RMS: Resource Management System
How to do local setup of RMS:
Prerequisite software’s: Make sure below software are installed

JDK 1.7 or above
Eclipse IDE
MySQL Server v5.5 or above
SQL Yog
Apache Tomcat 7
GIT
Checkout code in your local using GIT

URL: An http://yirmswin10pc:8080/gitbucket/git/root/rms.git
Clone the RMS project using GIT BASH or Turtoise GIT. step-1

Click on 'Git Clone' and paste clone URL. step-2

Click on 'OK' and clone the repository. step-3

Reporsitory will be cloned. step-3
Import project in eclipse as Maven project1.

In eclipse, Go to "Existing Maven Project" step-1

Browse the path of checkout project and click on finish. step-2

Add Tomcat server

Open new server window and choose tomcat and enter "Server Name" step-1

Select rms and move it to right side. step-2

Click on Open Launch configuration(Press F3 on server). step-3

Add below argument in VM Argument. -XX:+UseCompressedOops -XX:ReservedCodeCacheSize=96m -XX:+UseCodeCacheFlushing -ea -Dsun.io.useCanonCaches=false -Djava.net.preferIPv4Stack=true -DIafConfigSuffix=Local step-4
Click on Classpath:

 Add mysql-connector-java-5.1.9.jar by clicking Add External Jar
Click to download: mysql-connector-java-5.1.9.jar

step-5

Click on Apply and OK. step-6

Connect to database in context.xml.

 1. Add below code before </context > tag

`
 <Resource auth="Container" driverClassName="com.mysql.jdbc.Driver"
  initialSize="5" maxActive="120" maxIdle="5" maxWait="5000" name="jdbc/rms"
  password="root" poolPreparedStatements="true" type="javax.sql.DataSource"
  url="jdbc:mysql://inidrrmstsrv01:3306/rms_3.1_test?zeroDateTimeBehavior=convertToNull" username="root" validationQuery="select 1" />
`
Note: Please update DB details accordingly.

 ![step-1](setup_files/step_5_a.png)
Add below configuration changes in tomcat server’s web.xml file.`
mappedfile false </init-param
`    step-2
Add dto.jar at your respective location C:\Users\username\.m2\repository\org\yash\dto\1.6

Clean and build the project.

Clean tomcat and start. Use URL : http://localhost:8080/rms to see the dashboard of RMS final
