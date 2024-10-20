package fr.inria.verveine.extractor.java;

import fr.inria.verveine.extractor.java.utils.ImplicitVarBinding;
import org.junit.Before;
import org.junit.Test;
import org.moosetechnology.model.famix.famixjavaentities.ImplicitVariable;
import org.moosetechnology.model.famix.famixjavaentities.Method;
import org.moosetechnology.model.famix.famixtraits.TNamedEntity;

import java.util.Collection;

import static org.junit.Assert.*;

public class VerveineJTest_ImplicitVarBinding extends VerveineJTest_Basic {

    private Method mth1;
    private Method mth2;

    @Before
    public void setup() {
        mth1 = new Method();
        mth2 = new Method();
    }

    public VerveineJTest_ImplicitVarBinding() throws IllegalAccessException {
        super(false);
    }

    /**
     * Returns an ImplicitVariable instance for the given owner with the given name
     */
    protected ImplicitVarBinding getImplicitVar(Method owner, String name) {
        return ImplicitVarBinding.getInstance(owner, name);
    }

	@Test
	public void testUniqForMethod() {
		assertEquals(getImplicitVar(mth1, EntityDictionary.SELF_NAME), getImplicitVar(mth1, EntityDictionary.SELF_NAME));
		assertEquals(getImplicitVar(mth1, EntityDictionary.SUPER_NAME), getImplicitVar(mth1, EntityDictionary.SUPER_NAME));
	}

    @Test
	public void testDiffForMethods() {
		assertNotEquals(getImplicitVar(mth1, EntityDictionary.SELF_NAME), getImplicitVar(mth2, EntityDictionary.SELF_NAME));
		assertNotEquals(getImplicitVar(mth1, EntityDictionary.SUPER_NAME), getImplicitVar(mth2, EntityDictionary.SUPER_NAME));
	}

	@Test
	public void testSefDiffSuper() {
		assertNotEquals(getImplicitVar(mth1, EntityDictionary.SELF_NAME), getImplicitVar(mth1, EntityDictionary.SUPER_NAME));
	}

	@Test
    public void testAccessesInvocationsFromParse() {
        VerveineJParser parser = new VerveineJParser();
        repo = parser.getFamixRepo();
        parser.configure( new String[] {"-anchor" , "assoc", "test_src/generics/Dictionary.java"});
        parser.parse();

        Collection<ImplicitVariable> implicits = entitiesOfType( ImplicitVariable.class);
        assertEquals(4,   implicits.size());

        for (ImplicitVariable var : implicits) {
            switch (((TNamedEntity)var.getParentBehaviouralEntity()).getName()) {
                case "Dictionary" :
                    assertEquals(5, var.getIncomingAccesses().size());
// actually generates 6 accesses:
//5: 700/713 -> self -> mapbind     instead of famixRepo
//1: 733/744 -> mapBind      instead of mapBind
//4: 782/793  -> mapName  instead of mapName
//0: 848/861 ->  mapImpVar       instead of mapImpVar
//2: 919/930 -> self  -> mapbind       for mapBind
//3: 919/930 -> mapBind        for mapBind

                    assertEquals(0, var.getReceivingInvocations().size());
                    break;
                case "testMethodRettype" :
                    assertEquals(0, var.getIncomingAccesses().size());
                    assertEquals(1, var.getReceivingInvocations().size());
                    break;
                case "createFamixEntity" :
                    assertEquals(1, var.getIncomingAccesses().size());
                    assertEquals(1, var.getReceivingInvocations().size());
                    break;
                case "ensureFamixEntity" :
                    assertEquals(0, var.getIncomingAccesses().size());
                    assertEquals(1, var.getReceivingInvocations().size());
                    break;
                default :
                    fail("Unknown ImplicitVariable owner: " + ((TNamedEntity)var.getParentBehaviouralEntity()).getName());
            }
        }
    }
}
