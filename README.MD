# Getting Started

### Tehnologije korištene u aplikaciji 
* Thymeleaf
* Spring Security - user mgm
* Spring MVC
* Spring Data JPA-Hibernate na steroidima (Spring Data)
* MySQL DB


### Arhitektura aplikacije
* tradicionalni pristup Spring MVC - multipage not SPA
* Spring Boot - RAD(Rapid App Dev)

### Goruća pitanja 

#### Kako konfigurisati Spring Security ? 

1. Kako koristiti svoj login.html ?
2. Kako dodati logout opciju ?
3. Kako dodati register.html ?
4. Kako natjerati spring da priča sa našom user/User tabelom/entitetom u bazi ?



1. Odgovor 1. 2. 3.: `SecurityConfig`
   -  ``httpSecurity
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers("/register", "/login").permitAll()
                                .anyRequest().authenticated())``  => ovaj dio koda kaže da: "Bez auth pustiš pristup `/login` i `/register` dok pristup `/dashboard` page-u i svim dr. mora i dalje biti zaštićen "
   - `` .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/dashboard", true)
                        .failureUrl("/login?error=true")
                        .permitAll())`` => ovaj dio koda kaže spring-u da ne koristi svoj login.html nego da koristi naš.


<br><br>
U onom momentu kada smo uključili u našu Spring Boot aplikaciju
Spring Security kroz pom.xml :
`
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>
`<br>
Koje su posljedice uključenja Spring Security bile ? 
<br>
4. Odgovor:
- pristup svim @Controllerima je bio zaštićen tj. kada god bi pokušali pristupiti ili /dashboard ili /register ili /login Spring Security nas je redirectao automatski na svoj LOGIN PAGE.
- koji su kredencijali(username/password) automatski podešeni ? Odgovor: <b>Kredencijali postavljeni u application.properties </b> ``#Spring Security
spring.security.user.name=admin
spring.security.user.password=admin123``
- Problem koji smo imali s tim kredencijalima je koji ? Odgovor: <b> Imamo samo jedan korisnički nalog username=admin & password=admin123</b>
- Gdje su sačuvani gore navedeni kredencijali ? Odgovor: NISU U BAZI. Sačuvani su u inMemory objektu koji postoji dok se aplikacija izvršava. Taj objekat se zove ``UserDetails`` a pristupa mu se preko ``UserDetailsService``
- Mi moramo napraviti ADAPTACIJU tog `UserDetails` objekta sa našom bazom odnosno pravimo `UserDetailsAdapter implements UserDetails` a u kompoziciji ima `User`. UserDetailsAdapter IS A `UserDetails` and HAS A `User`.
- Dalje moramo Springu kazati da koristi našu implementaciju `UserDetails` objekta tj. da koristi `UserDetailsAdapter`. Kako ovo radimo ?  ODGOVOR: Implementiramo Spring Interface koji se zove `UserDetailsService` i kažemo koristi naš `CustomUserDetailsService` koji će koristiti naš `UserDetailsAdapter`.
- Kako sada da kažemo Springu "Koristi našu implementaciju `CustomUserDetailsService`" ? ODGOVOR: Kroz `SecurityConfig` ?
  1. injektujemo `CustomUserDetailsService customUserDetailsService;` u klasu koja ima anotaciju `Confgiuration`(SecurityConfig klasa)
  2. Onda kroz `AuthenticationProvider` bean kažemo Springu
     - koristi naš `customUserDetailsService`
     - koristi naš `bCryptPasswordEncoder`




