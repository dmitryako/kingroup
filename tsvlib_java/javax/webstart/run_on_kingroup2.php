<?php header("Content-type: application/x-java-jnlp-file");
      echo "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
?>

<?xml version="1.0" encoding="UTF-8"?>
<jnlp spec="1.0+"
  codebase="http://www.it.jcu.edu.au/kingroup/dev/webstart"
>
<information>
  <title>Time Check</title>
  <vendor>Java Developer Connection</vendor>
  <homepage href="/jdc" />
  <description>Demonstration of JNLP</description>
</information>
<offline-allowed/>
<security>
  <j2ee-application-client-permissions/>
</security>
<resources>
  <j2se version="1.2+" />
  <jar href="JNLPTime.jar"/>
</resources>
<application-desc main-class="TheTime" />
</jnlp>

