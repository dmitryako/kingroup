package kingroup.model;
/* Copyright (C) 2004  Dr. Dmitry Konovalov.
 * This code is licensed under the GPL license (see www.gnu.org) for academic,
 * not-for-profit use or for use within other Open Source software (see www.opensource.org).
 * See www.kingroup.org for more details.
 */

import kingroup.model.KinshipIBDModelV1;

import junit.framework.*;

/**
 * Testing <code>kingroup.model.KinshipIBDModelV1</code>.
 *
 */
public class KDescentIdentityBeanTest extends TestCase {
    private int count_ = 0;
    private float delta_ = 1e-8f;
    private float rm_ = count_++;
    private float rmm_ = count_++;
    private float rp_ = count_++;
    private float rmp_ = count_++; // different values
    private int nrm_ = count_++;
    private int nrp_ = count_++;
    private boolean co_ = true; 
    
    public static void main(String[] args) {
        junit.textui.TestRunner.run(suite());
    }
    public static Test suite() {
        return new TestSuite(KDescentIdentityBeanTest.class);
    }
    protected void setUp() {    }
    
    
    final public void testCopyTo() {
        KinshipIBDModelV1 data = new KinshipIBDModelV1();
        setNewAttributes(data);
        
        KinshipIBDModelV1 data2 = new KinshipIBDModelV1(data);
        assertAttributes(data2); // data == data2

        setNewAttributes(data); // data != data2
        data.copyTo(data2); // data == data2
        assertAttributes(data); 
        assertAttributes(data2); 
    }
    
    public void testGetSet() {
        KinshipIBDModelV1 data = new KinshipIBDModelV1();
        
        setNewAttributes(data);        
        assertAttributes(data);
        
        setNewAttributes(data);        
        assertAttributes(data);
    }

    private void setNewAttributes(KinshipIBDModelV1 data) {
        generateNewAttributes();
        
        data.setRm(rm_);
        data.setRp(rp_);
        data.setRmMax(rmm_);
        data.setRpMax(rmp_);
        data.setNumRms(nrm_);
        data.setNumRps(nrp_);
        data.setComplex(co_);
    }
    
    private void assertAttributes(KinshipIBDModelV1 data) {
        assertEquals(rm_, data.getRm(), delta_);
        assertEquals(rp_, data.getRp(), delta_);
        assertEquals(rmm_, data.getRmMax(), delta_);
        assertEquals(rmp_, data.getRpMax(), delta_);
        assertEquals(nrm_, data.getNumRms());
        assertEquals(nrp_, data.getNumRps());
        assertEquals(co_, data.getComplex());
    }
    
    private void generateNewAttributes() {
        rm_ = count_++;
        rmm_ = count_++;
        rp_ = count_++;
        rmp_ = count_++;
        nrm_ = count_++;
        nrp_ = count_++;
        co_ = !co_;
    }
}

