package GrupoF.Proyecto3.Seguridad;

import GrupoF.Proyecto3.Servicios.ClienteServicio;
import GrupoF.Proyecto3.Servicios.ProveedorServicio;
import GrupoF.Proyecto3.Servicios.UsuarioServicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SeguridadWeb extends WebSecurityConfigurerAdapter {
    @Autowired
    public UsuarioServicio us;
    @Autowired
    public ProveedorServicio pS;
    
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(us)
                .passwordEncoder(new BCryptPasswordEncoder());
    }
            
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        
        http
            .authorizeRequests()
                .antMatchers("/admin/*").hasRole("ROL_ADM")
                .antMatchers("/cliente/*").hasRole("ROL_USUARIO")
                .antMatchers("/proveedor/*").hasRole("ROL_USUARIO")
                .antMatchers("/index").hasAnyRole()
                .antMatchers("/css/*", "/js/*", "/img/*", "/**")
                .permitAll()
            .and().formLogin()
                    .loginPage("/ingreso")
                    .loginProcessingUrl("/ingresoChk")
                    .usernameParameter("correo")
                    .passwordParameter("contrasenia")
                    .defaultSuccessUrl("/usuario/sesion")
                    .permitAll()
                .and().logout()
                        .logoutUrl("/salir")
                        .logoutSuccessUrl("/?notificacion='Sesión cerrada Exitosamente'")
                        .permitAll()
                .and().csrf()
                        .disable();                
    }
}
