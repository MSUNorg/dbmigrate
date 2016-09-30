# dbmigrate
mysql 数据迁移工具 
用户移民工具

### 安裝本地依赖包
`mvn clean`，然后`mvn -B -DskipTests clean dependency:list install`

### 下载依赖 & 测试
`mvn clean install`

### 打包
`mvn clean package -DskipTests`

###打包后目录结构`tree`命令查看
```
└── target
    └── dbmigrate-1.0.0-SNAPSHOT
        ├── conf
        │   ├── xxxx.properties
        │   ├── xxxx.properties
        │   ├── log4j.properties
        │   ├── static
        │   └── templates
        ├── lib
        │   ├── xxxx-1.0.jar
        │   ├── dbmigrate-1.0.0-SNAPSHOT-2016093005.jar
        │   ├── xxxx-2.10.0.jar
        │   └── xxxx-1.4.01.jar
        └── start_dbmigrate.sh
```