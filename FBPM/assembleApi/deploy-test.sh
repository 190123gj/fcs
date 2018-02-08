project_name=assembleApi
tomcat_name=assembleApi
project_path=/webapps/jck/${project_name}
tomcat_path=/opt/apache-tomcat-7.0.39-${tomcat_name}

echo project name is ${project_name}
echo project path is ${project_path}

echo '更新svn ... '
cd  ${project_path};
svn update --username wangyf --password wangyf;
echo '开始 maven clean ... ';
mvn clean;
echo '开始 maven 打包 ... ';
mvn install -Dmaven.test.skip=true -P test

pid=`ps aux | grep ${tomcat_name} | grep -v grep | awk '{print $2}'`
echo '停止tomcat 服务  pid=${pid}... '

${tomcat_path}/bin/shutdown.sh;
sleep 2;
if [ ! -z "$pid" ]; then
	kill -9 $pid
	echo 'kill tomcat'
fi

echo '清除 tomcat 日志 ... ';
rm -rf /soft/var/log/fcs/${tomcat_name}/;
rm -rf ${tomcat_path}/logs/* ;
rm -rf ${tomcat_path}/webapps/*;
rm -rf ${tomcat_path}/work/* ;

echo '开始复制 ROOT.war'
war_file_path=${project_path}/assembleApi-assemble/target/ROOT.war
cp -r ${war_file_path} ${tomcat_path}/webapps/

echo '启动tomcat服务'
${tomcat_path}/bin/startup.sh;
sleep 1;
pid=`ps aux | grep ${tomcat_name} | grep -v grep | awk '{print $2}'`
echo "${tomcat_name} running with pid=${pid}"

cd /soft/var/log/fcs/${project_name}
tail -f ${project_name}.log
