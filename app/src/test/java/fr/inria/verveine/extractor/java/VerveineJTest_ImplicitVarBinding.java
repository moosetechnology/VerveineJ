package fr.inria.verveine.extractor.java;

import fr.inria.verveine.extractor.java.utils.ImplicitVarBinding;
import org.junit.Before;
import org.junit.Test;
import org.moosetechnology.model.famix.famixjavaentities.Class;
import org.moosetechnology.model.famix.famixjavaentities.ImplicitVariable;
import org.moosetechnology.model.famix.famixjavaentities.Method;
import org.moosetechnology.model.famix.famixtraits.TNamedEntity;
import org.moosetechnology.model.famix.famixtraits.TInvocation;

import java.util.Collection;

import static org.junit.Assert.*;

public class VerveineJTest_ImplicitVarBinding extends VerveineJTestAbstract {

    private Method mth1;
    private Method mth2;

    @Before
    public void setup() {
        mth1 = new Method();
        mth2 = new Method();
    }

    /**
     * Returns an ImplicitVariable instance for the given owner with the given name
     */
    protected ImplicitVarBinding getImplicitVar(Method owner, String name) {
        return ImplicitVarBinding.getInstance(owner, name);
    }

	@Test
	public void testUniqForMethod() {
		assertEquals(getImplicitVar(mth1, EntityDictionary.THIS_NAME), getImplicitVar(mth1, EntityDictionary.THIS_NAME));
		assertEquals(getImplicitVar(mth1, EntityDictionary.SUPER_NAME), getImplicitVar(mth1, EntityDictionary.SUPER_NAME));
	}

    @Test
	public void testDiffForMethods() {
		assertNotEquals(getImplicitVar(mth1, EntityDictionary.THIS_NAME), getImplicitVar(mth2, EntityDictionary.THIS_NAME));
		assertNotEquals(getImplicitVar(mth1, EntityDictionary.SUPER_NAME), getImplicitVar(mth2, EntityDictionary.SUPER_NAME));
	}

	@Test
	public void testSefDiffSuper() {
		assertNotEquals(getImplicitVar(mth1, EntityDictionary.THIS_NAME), getImplicitVar(mth1, EntityDictionary.SUPER_NAME));
	}

	@Test
	public void testImplicitVariableProperties() {
		VerveineJParser parser = new VerveineJParser();
		repo = parser.getFamixRepo();
		parser.configure(new String[] { "src/test/resources/ad_hoc/ImplicitVariableProperties.java" });
		parser.parse();

		Class child = detectFamixElement(Class.class, "ImplicitVariableChild");
		Class parent = detectFamixElement(Class.class, "ImplicitVariableParent");
		Method method = detectFamixElement(Method.class, "method");
		ImplicitVariable thisVariable = null;
		ImplicitVariable superVariable = null;
		for (ImplicitVariable variable : entitiesOfType(ImplicitVariable.class)) {
			if (variable.getParentBehaviouralEntity() == method) {
				if (EntityDictionary.THIS_NAME.equals(variable.getName())) {
					thisVariable = variable;
				} else if (EntityDictionary.SUPER_NAME.equals(variable.getName())) {
					superVariable = variable;
				}
			}
		}

		assertNotNull(thisVariable);
		assertNotNull(superVariable);
		assertFalse(thisVariable.getIsStub());
		assertFalse(superVariable.getIsStub());
		assertSame(child, thisVariable.getDeclaredType());
		assertSame(child, superVariable.getDeclaredType());

		for (TInvocation invocation : method.getOutgoingInvocations()) {
			if (invocation.getReceiver() == superVariable) {
				assertSame(parent, ((Method) firstElt(invocation.getCandidates())).getParentType());
				return;
			}
		}
		fail("No invocation on super");
	}

	@Test
    public void testAccessesInvocationsFromParse() {
        VerveineJParser parser = new VerveineJParser();
        repo = parser.getFamixRepo();
        parser.configure( new String[] {"-anchor" , "assoc", "src/test/resources/generics/Dictionary.java"});
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
