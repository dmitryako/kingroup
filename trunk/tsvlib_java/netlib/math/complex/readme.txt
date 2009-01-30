                       README for JavaComplex.zip
                       --------------------------
                     ORG.netlib.math.complex.Complex
                        version 1.0.1 March 2001

                                CONTENTS
                                --------
                            * WHAT YOU GOT
                            * INSTALLING IT
                            * CHECKING IT'S OK
                            * MORE CHECKING
                            * RELEASE NOTES


                            
* WHAT YOU GOT
--------------

    You've downloaded JavaComplex.tgz from Netlib, or at least, got hold
of a copy.  I assume that you have already unzipped the download.

    JavaComplex.tgz when unzipped expands to

    
        JavaComplex
        |
        +---ORG
            +---netlib
                +---math
                    +---complex
                        |   Complex.class
                        |   ComplexBenchmark.class
                        |   TestComplex.class
                        |
                        |   Complex.java
                        |   ComplexBenchmark.java
                        |   TestComplex.java
                        |
                        |   Complex.html
                        |   GNU_GeneralPublicLicence.html
                        |   readme.txt
                        |
                        +---imagery
                                creamp.gif
                                gnu-head.png
                                netlib2.gif
                                SeeComplex.jar

                                

* INSTALLING IT
---------------

    In  order to use Netlib's Complex class, you must make sure that the
class is accessible from your  local  machine's  Java  CLASSPATH.   (The
"CLASSPATH"  is a system environment variable that must be set correctly
for Java stuff to work properly on your system.  I  assume  you  already
know  about  it  --   I  know  from  bitter experience that it can cause
exasperating problems for people who are just beginning  to  use  Java.)
There  are  two  ways to go about making the Complex class accessible on
your machine, one "bad", and one good.  The "bad" way is  to  yet  again
add a new folder to your CLASSPATH. I try not to do this.

    And  the  good  way  is  to have in your CLASSPATH a general package
directory, where third-party packages can simply be downloaded and  then
"slotted  in" (ie. moved into the proper directory location).  Here's an
illustration from my own system:

    This is my CLASSPATH on Windows NT:

    
        CLASSPATH=.;G:\java\bin;G:\packages

        
    In  the folder "G:\packages", I have -- slightly simplified for this
readme letter -- four subfolders:


        COM
        JavaCC
        ORG
        test.my

        
    Down  the COM folder, at present, is just ObjectSpace's Java Generic
Library.  Down the ORG folder is, surprise, surprise, _already_ Netlib's
Complex Package.  (JavaCC is a compiler-compiler tool from Metamata, and
test.my is obviously where I try things out.)

    OK,  so  the  good  way to make Netlib's Complex class accessible on
your local machine is to "slot in" (eg. move) the entire contents of our
newly  downloaded  ORG  folder  to  our general package directory, where
third-party Java packages sit (making sure we're not wiping the contents
of any existing folders, but just adding to them).

    To  illustrate  again,  under Windows (or MacOS, or KDE, or GNOME) I
myself would open the newly unzipped folder  called  "JavaComplex",  and
see  -- aha! -- that it contains a folder called "ORG".  Then, using the
mouse and the  graphical  interface,  I'd  "drag  and  drop"  the  newly
downloaded ORG folder into my existing "packages" folder.  Alternatively
I might enter a command (I use Cygwin UNIX under NT), something like:


        > cp  -Ri  c:/unzipped/JavaComplex/ORG  g:/packages

        
    Anyway -- illustrating again from my own system -- down the packages
folder I have:


        packages
        |
        +---COM
        |   +---objectspace
        |       +---jgl
        |
        +---JavaCC
        |   +---bin
        |
        +---ORG
        |   +---netlib
        |       +---math
        |           +---complex
        |               +---imagery
        |
        +---test.my
            +---util

            
    Next time I download a third-party package, it will neatly slot into
this scheme somewhere, probably either down the COM or the ORG  folders.
Very possibly, it could be another package from Netlib.



* CHECKING IT'S OK
------------------

    Once  you  have  got  Netlib's  Complex  class  slotted  in  to your
CLASSPATH, in the manner I've just illustrated, and done  any  rebooting
(if  that's necessary on your system), you're now free to browse through
the Complex class' API documentation ("Complex.html"),  run  the  tester
program  for  it, and, well, it's ready to be used in your Java work.  A
quick check by the way, to reassure ourselves that everything's now  OK,
is to enter the command line:


        > java ORG.netlib.math.complex.Complex

        
and you should get


        Module : ORG.netlib.math.complex.Complex
        Version: 1.0.1
        Date   : Fri 23-Mar-2001 8:56 pm
        Author : sandy@almide.demon.co.uk
        Remark : Class available from http://www.netlib.org/

        Hint:  use TestComplex to test the class.

        
in reply.



* MORE CHECKING
---------------

    Besides  the  Complex  class, two supplementary classes are supplied
with the ORG.netlib.math.complex package:


    1)  TestComplex

        This  is a command-line based tester for class Complex.  You can
        test out any of the methods in the API.  To invoke it, enter

        
            > java ORG.netlib.math.complex.TestComplex

            
        and  follow  the usage instructions.  When the work you're doing
        closely involves complex number arithmetic, you  may  find  that
        when  used  alongside  pencil  and paper, you start viewing this
        tool (as I do!) as  a  convenient  command  line  based  complex
        number "calculator".

        
    2)  ComplexBenchmark

        This  tests the speed of execution of all the functions in class
        Complex, on the current local system.  It calls each function  a
        set  number of iterations.  The default is 1 million.  To invoke
        it, enter

        
            > java ORG.netlib.math.complex.ComplexBenchmark
        or
            > java ORG.netlib.math.complex.ComplexBenchmark <iterations>

            
        Have patience, this can take some time on older hardware.


        
* RELEASE NOTES
---------------

    o   The JavaComplex package was first publicly released under Netlib
        back in August 1997 as "version 1.0".

    o   Then  in  March  2001,  after  it was pointed out in an email, I
        fixed a problem with Complex's implementation of  Cloneable  (by
        actually  adding  a public Object clone() method!).  At the same
        time I changed the licensing to the more seriously  professional
        "GNU General Public License".


                             - - - X - - -


    We  all  live  in the real world.  I began writing the Complex class
"example" in order to rid myself  of  the  sinking  feeling  whenever  I
thought  of  anything  related to my old college ("University") in North
London.  My three year stint there was  the  most  ghastly,  stultifying
experience.   Writing  Complex helped me exorcise its ghost.  That's the
plain truth.  At the time, Java was in its infancy.  One  big  area  for
improving the scope of Java was in engineering.  Engineers need computer
systems with complex arithmetic.  At  the  same  time,  I  knew  that  I
couldn't  address  this  need  single-handedly.  So the Complex class is
designed primarily to  be  a  good  example.   An  example  of  correct,
efficient, open-source code. Its documentation is also carefully crafted
so as to speak as informatively to the complete newbie undergraduate, as
to  the bearded professor of Mathematics, or grizzled numerical-software
engineer.

    In the final stages before version 1.0 was released, I  was  working
very  heavily with the classic "Numerical Recipes in Fortran 77: The Art
of  Scientific  Computing"  (ISBN  0-521-43064-X)  and  the   derivative
"Numerical  Recipes  in  C"  (ISBN 0-521-43108-5).   These tomes contain
some serious errors.  They were pointed out by the abstract algebraicist
Mr. Daniel Hirsch, who was my old maths teacher from a previous college,
now  retired.   I  would  never  have  finished  the  final  stages   of
mathematical debugging without him.

    Many,  many  thanks to Mr. Daniel Hirsch, for his constant advice on
the mathematics, his exasperating ability to uncover bugs blindfold, and
for  his  persistent  badgering  over  the  exact wording of the Complex
class' documentation.

    For instance, he starts to growl like a badger if you say  "infinite
set".

    "Grrr...What's _that_ mean?  _Countably_ infinite?"

    You think for a while.

    "Grrr..."

    "Yes."

    "Ah!  Then you mean _infinitely many_."


    
With Best Wishes,


Sandy

/*             C A U T I O N   E X P L O S I V E   B O L T S
--                     REMOVE BEFORE ENGAGING REPLY
//
// Kelly and Sandy Anderson <kelsan@explosive-alma-services-bolts.co.uk>
// (alternatively            kelsan_odoodle at ya who period, see oh em)
// Alexander (Sandy)  1B5A DF3D A3D9 B932 39EB  3F1B 981F 4110 27E1 64A4
// Kelly              673F 6751 6DBA 196F E8A8  6D87 4AEC F35E E9AD 099B
// Homepages             http://www.explosive-alma-services-bolts.co.uk/
*/
