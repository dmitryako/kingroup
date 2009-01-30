cd ..
javac org/ledax/LedaWeightAssignmentJNI.java
javah -help
javah -javax.jni ledax.LedaWeightAssignmentJNI
move *LedaWeightAssignmentJNI* javax.jni
pause