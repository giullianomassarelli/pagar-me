# Pagar-Me API
Projeto desenvolvido como treinamento no gerador de devs

### Pré-requisitos
O que você precisa instalar para rodar o projeto?
* [Docker](https://www.docker.com/)
* [Docker-Compose](https://docs.docker.com/compose/)
* [Gradle](https://gradle.org/)
* [JDK-17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
* [JRE-17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)

### Instalação
git clone git@github.com:giullianomassarelli/pagar-me.git

### Docker Compose Build and Run como Perfil de Dev
```
sh docker-compose-dev.sh
```

### Para acessar a documentação do Swagger:
```
http://localhost:8080/swagger-ui.html#/
```

### Tecnologias utilizadas

* [Gradle](https://gradle.org/) - De aplicativos móveis a microsserviços, de pequenas empresas a grandes empresas, a Gradle ajuda as equipes a construir, automatizar e fornecer software melhor, mais rapidamente.
* [Spring Boot Web Starter](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-web) - Starter para construção de web, incluindo aplicativos RESTful, usando o Spring MVC. Usa o Tomcat como o contêiner incorporado padrão
* [Lombok](https://projectlombok.org/) - O Projeto Lombok é uma biblioteca java que se conecta automaticamente ao seu editor e cria ferramentas, apimentando seu java. Nunca escreva outro método getter ou equals novamente, com uma anotação sua classe tem um construtor com todos os recursos, Automatize suas variáveis ​​de registro e muito mais.
* [Spring Boot Test Starter](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-test) - Starter para testar aplicativos Spring Boot com bibliotecas, incluindo JUnit, Hamcrest e Mockito.
* [Model Mapper](http://modelmapper.org/) - Os aplicativos geralmente consistem em modelos de objetos semelhantes, mas diferentes, em que os dados em dois modelos podem ser semelhantes, mas a estrutura e as preocupações dos modelos são diferentes. O mapeamento de objetos facilita a conversão de um modelo em outro, permitindo que modelos separados permaneçam segregados.
* [Spring Cloud Starter OpenFeign](https://cloud.spring.io/spring-cloud-openfeign/reference/html/) - Este projeto fornece integrações OpenFeign para aplicativos Spring Boot por meio de configuração automática e ligação ao Spring Environment e a outras expressões do modelo de programação Spring.
* [Swagger](https://swagger.io/) - Simplifique o desenvolvimento de API para usuários, equipes e empresas com o conjunto de ferramentas open source e profissional Swagger.
* [Power Mock](https://powermock.github.io/) - O PowerMock é uma estrutura que estende outras bibliotecas simuladas, como o EasyMock, com recursos mais poderosos. O PowerMock usa um carregador de classes personalizado e manipulação de bytecode para permitir a simulação de métodos estáticos, construtores, classes e métodos finais, métodos particulares, remoção de inicializadores estáticos e muito mais.