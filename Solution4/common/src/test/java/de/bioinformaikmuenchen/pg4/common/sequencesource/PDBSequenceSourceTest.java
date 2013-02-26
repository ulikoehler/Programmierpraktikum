/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.bioinformaikmuenchen.pg4.common.sequencesource;

import de.bioinformatikmuenchen.pg4.common.Sequence;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author koehleru
 */
public class PDBSequenceSourceTest {

    public PDBSequenceSourceTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getSequence method, of class PDBSequenceSource.
     */
    @Test
    public void testGetSequence() {
        PDBSequenceSource source = new PDBSequenceSource();
        //1JUL
        Sequence result = source.getSequence("1JUL");
        assertEquals(result.getSequence(), "MPRYLKGWLKDVVQLSLRRPSFRASRQRPIISLNERILEFNKRNITAIIAEYKRKSPSGLDVERDPIEYSKFMERYAVGLSILTEEKYFNGSYETLRKIASSVSIPILMKDFIVKESQIDDAYNLGADTVLLIVKILTERELESLLEYARSYGMEPLIEINDENDLDIALRIGARFIGINSRDLETLEINKENQRKLISMIPSNVVKVAESGISERNEIEELRKLGVNAFLIGSSLMRNPEKIKEFIL");
        //1ULI
        result = source.getSequence("1ULI");
        assertEquals(result.getSequence(), "MTDVQCEPALAGRKPKWADADIAELVDERTGRLDPRIYTDEALYEQELERIFGRSWLLMGHETQIPKAGDFMTNYMGEDPVMVVRQKNGEIRVFLNQCRHRGMRICRADGGNAKSFTCSYHGWAYDTGGNLVSVPFEEQAFPGLRKEDWGPLQARVETYKGLIFANWDADAPDLDTYLGEAKFYMDHMLDRTEAGTEAIPGIQKWVIPCNWKFAAEQFCSDMYHAGTTSHLSGILAGLPDGVDLSELAPPTEGIQYRATWGGHGSGFYIGDPNLLLAIMGPKVTEYWTQGPAAEKASERLGSTERGQQLMAQHMTIFPTCSFLPGINTIRAWHPRGPNEIEVWAFTVVDADAPEEMKEEYRQQTLRTFSAGGVFEQDDGENWVEIQQVLRGHKARSRPFNAEMGLGQTDSDNPDYPGTISYVYSEEAARGLYTQWVRMMTSPDWAALDATRPAVSESTHT");
    }
}