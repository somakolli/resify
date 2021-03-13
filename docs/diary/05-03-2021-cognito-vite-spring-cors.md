# 05-03-2021

## Cognito vite build problem

Had a problem with the cognito build. Apparently the build optimiser does not support libraries which use built in node modules.
Webpack4 includes those for you but ultimately they are backend modules and should not be used in client libraries.
See: https://github.com/vitejs/vite/issues/1374#issuecomment-754820938

Amazon uses node libraries a lot in their aws-sdk. I tried to make it work with rollup plugins but I couldn't for a few hours and decided to ditch vite and go back to vue-cli,
until amazon fixes their sdk. See: https://twitter.com/sam_martinez22/status/1346541484539957248.

## Spring boot cors problem

There was a problem with apring boot and preflight requests.
By default Spring requires preflight requests to be authenticated, but to add a authentication token to the header you need make a preflight request to check if the headers are allowed.
Luckily the magicians at bealding knew a solution: https://www.baeldung.com/spring-security-cors-preflight.

The resulting code configured for oauth2 resource server is:

```
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().anyRequest().authenticated().and().oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);
        http.cors();
    }
}
```

## Done with login
At least I am done with the basic login functionality for now.
Now I just need to integrate the web views with the backend and I'm done.

## Started integrating views
I need to figure out a way to update the data when new data is created.
I can use websockets with spring, but that will take a lot of time to just configure.
For now I think I will just use swrv and update when I create something.

## Todo for tomorow
- integrate all views with backend
- check on endpoints for authorization