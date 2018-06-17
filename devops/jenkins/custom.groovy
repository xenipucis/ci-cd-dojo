// import com.cloudbees.plugins.credentials.impl.*;
// import com.cloudbees.plugins.credentials.*;
// import com.cloudbees.plugins.credentials.domains.*;

// String keyfile = "/tmp/key"

// Credentials c = (Credentials) new UsernamePasswordCredentialsImpl(CredentialsScope.GLOBAL,java.util.UUID.randomUUID().toString(), "description", "user", "password")


// def ksm1 = new CertificateCredentialsImpl.FileOnMasterKeyStoreSource(keyfile)
// Credentials ck1 = new CertificateCredentialsImpl(CredentialsScope.GLOBAL,java.util.UUID.randomUUID().toString(), "description", "password", ksm1)

// def ksm2 = new CertificateCredentialsImpl.UploadedKeyStoreSource(keyfile)
// Credentials ck2 = new CertificateCredentialsImpl(CredentialsScope.GLOBAL,java.util.UUID.randomUUID().toString(), "description", "password", ksm2)

// SystemCredentialsProvider.getInstance().getStore().addCredentials(Domain.global(), c)
// SystemCredentialsProvider.getInstance().getStore().addCredentials(Domain.global(), ck1)
// SystemCredentialsProvider.getInstance().getStore().addCredentials(Domain.global(), ck2)

import jenkins.model.*
import com.cloudbees.hudson.plugins.folder.*;
import com.cloudbees.hudson.plugins.folder.properties.*;
import com.cloudbees.hudson.plugins.folder.properties.FolderCredentialsProvider.FolderCredentialsProperty;
import com.cloudbees.plugins.credentials.impl.*;
import com.cloudbees.plugins.credentials.*;
import com.cloudbees.plugins.credentials.domains.*;

jenkins = Jenkins.instance

String id = java.util.UUID.randomUUID().toString()
Credentials c = new UsernamePasswordCredentialsImpl(CredentialsScope.GLOBAL, id, "description:"+id, "user", "password")

for (folder in jenkins.getAllItems(Folder.class)) {
  if(folder.name.equals('FolderName')){
	AbstractFolder<?> folderAbs = AbstractFolder.class.cast(folder)
    FolderCredentialsProperty property = folderAbs.getProperties().get(FolderCredentialsProperty.class)
    property.getStore().addCredentials(Domain.global(), c)
    println property.getCredentials().toString()
  }
}