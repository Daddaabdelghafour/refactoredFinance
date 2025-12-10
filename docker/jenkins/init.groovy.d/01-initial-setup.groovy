import jenkins.model.*
import hudson.security.*
import jenkins.install.InstallState // FIXED: Removed space

def instance = Jenkins.getInstance()

// Set up security realm (Internal user database)
def hudsonRealm = new HudsonPrivateSecurityRealm(false)
hudsonRealm.createAccount("admin", "admin") // FIXED: Removed space in 'createAccount'
instance.setSecurityRealm(hudsonRealm)

// Set up authorization strategy (Who can do what)
def strategy = new FullControlOnceLoggedInAuthorizationStrategy()
strategy.setAllowAnonymousRead(false)
instance.setAuthorizationStrategy(strategy)

// Mark the installation as complete to skip the wizard
instance.setInstallState(InstallState.INITIAL_SETUP_COMPLETED) // FIXED: Removed space

// Configure Jenkins URL
def jenkinsLocationConfiguration = JenkinsLocationConfiguration.get()
jenkinsLocationConfiguration.setUrl("http://localhost:8080/")
jenkinsLocationConfiguration.save()

instance.save()

println "Jenkins initial setup completed"
println "Admin user: admin"
println "Admin password: admin"