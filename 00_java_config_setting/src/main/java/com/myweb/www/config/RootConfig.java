package com.myweb.www.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@ComponentScan(basePackages = {"com.myweb.www.service"})
@MapperScan(basePackages = {"com.myweb.www.repository"})
public class RootConfig {
	// 컴포넌트가 등록되어있지 않더라도 땡겨 쓸 수 있는 어노테이션 (inject와 비슷하나 약간 다름)
	@Autowired
	ApplicationContext applicationContext;
	
	// root-context의 bean객체 -> method로 제공
	@Bean
	public DataSource dataSource() {
		HikariConfig hikariConfig = new HikariConfig();
		hikariConfig.setDriverClassName("net.sf.log4jdbc.sql.jdbcapi.DriverSpy");
		hikariConfig.setJdbcUrl("jdbc:log4jdbc:mysql://localhost:3306/springdb?serverTimezone=Asia/Seoul");
		hikariConfig.setUsername("springuser");
		hikariConfig.setPassword("mysql");
		hikariConfig.setMinimumIdle(5);	// 5초
		
		hikariConfig.setConnectionTestQuery("SELECT now()"); 	// db에 정상적으로 접근 되면 테스트로 얘를 띄움
		hikariConfig.setPoolName("springHikariCP");	// 내가 임의대로 지은 것임
		
		// 메뉴얼사이트에 있음
		hikariConfig.addDataSourceProperty("dataSource.cachePrepStmts", "true");	
		hikariConfig.addDataSourceProperty("dataSource.prepStmtCacheSize", "200");
		hikariConfig.addDataSourceProperty("dataSource.prepStmtCacheSqlLimit", "true");	// 과부하 방지
		hikariConfig.addDataSourceProperty("dataSource.useServerPrepStmts", "true");
		
		HikariDataSource hikariDataSource = new HikariDataSource(hikariConfig);
		return hikariDataSource;
	}
	
	@Bean
	public SqlSessionFactory sqlSessionFactory() throws Exception {
		SqlSessionFactoryBean sqlFactoryBean = new SqlSessionFactoryBean();
		sqlFactoryBean.setDataSource(dataSource());
		sqlFactoryBean.setConfigLocation(applicationContext.getResource("classpath:/MyBatisConfig.xml"));	// Mybatis config 등록
		sqlFactoryBean.setMapperLocations(applicationContext.getResources("classpath:/mappers/*.xml"));	// 얜 배열형으로 복수로 처리하기 때문에 getResources
		return (SqlSessionFactory)sqlFactoryBean.getObject();	// 알아서 잘 가겠지만 형변환을 해주자
	}
}
