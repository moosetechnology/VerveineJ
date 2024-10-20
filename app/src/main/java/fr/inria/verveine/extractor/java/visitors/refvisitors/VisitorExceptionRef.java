package fr.inria.verveine.extractor.java.visitors.refvisitors;

import fr.inria.verveine.extractor.java.EntityDictionary;
import fr.inria.verveine.extractor.java.VerveineJOptions;
import fr.inria.verveine.extractor.java.utils.NodeTypeChecker;
import org.eclipse.jdt.core.dom.*;
import org.moosetechnology.model.famix.famixjavaentities.ContainerEntity;
import org.moosetechnology.model.famix.famixjavaentities.Exception;
import org.moosetechnology.model.famix.famixjavaentities.Method;
import org.moosetechnology.model.famix.famixjavaentities.Package;
import org.moosetechnology.model.famix.famixjavaentities.ParameterType;
import org.moosetechnology.model.famix.famixtraits.TNamedEntity;
import org.moosetechnology.model.famix.famixtraits.TThrowable;
import org.moosetechnology.model.famix.famixtraits.TType;

import java.util.List;

/** A visitor to record exceptions declared/thrown/caught.<br>
 * It is simpler than the other ref visitors because we only need to worry about methods
 * @author anquetil
 */
public class VisitorExceptionRef extends AbstractRefVisitor {

    public VisitorExceptionRef(EntityDictionary dico, VerveineJOptions options) {
        super(dico, options);
    }


    protected Package visitCompilationUnit(CompilationUnit node) {
        Package fmx = null;
        PackageDeclaration pckg = node.getPackage();
        if (pckg == null) {
            fmx = dico.getFamixPackageDefault();
        } else {
            fmx = (Package) dico.getEntityByKey(pckg.resolveBinding());
        }
        this.context.pushPckg(fmx);

        return fmx;
    }

    protected void endVisitCompilationUnit(CompilationUnit node) {
        this.context.popPckg();
        super.endVisit(node);
    }

    @Override
    public boolean visit(TypeDeclaration node) {
        if (visitTypeDeclaration( node) != null) {
            return super.visit(node);
        } else {
            return false;
        }
    }

    @Override
    public void endVisit(TypeDeclaration node) {
        endVisitTypeDeclaration(node);
    }

	public boolean visit(MethodDeclaration node) {
		Method fmx = visitMethodDeclaration( node);
		if (fmx != null) {
		    for (Type excep : (List<Type>) node.thrownExceptionTypes()) {
		    	TThrowable excepFmx =  dico.asException(this.referedType(excep, (ContainerEntity) context.topType(), true, true));
		    	dico.createFamixDeclaredException(fmx,excepFmx);
            }
			return super.visit(node);
		} else {
			return false;
		}
	}

	@Override
	public void endVisit(MethodDeclaration node) {
		endVisitMethodDeclaration(node);
	}

    @Override
    public boolean visit(CatchClause node) {
        Method meth = (Method) this.context.topMethod();
        Type excepClass = node.getException().getType();
        if (meth != null) {
            TThrowable excepFmx = null;
            if ( NodeTypeChecker.isSimpleType(excepClass) || NodeTypeChecker.isQualifiedType(excepClass) ) {
                excepFmx = dico.asException(referedType(excepClass, meth, true, true));
            }
            if (excepFmx != null) {
            	dico.createFamixCaughtException(meth, excepFmx);
            }
        }

        return super.visit(node);
    }

    @Override
    public boolean visit(ThrowStatement node) {
        Method meth = (Method) this.context.topMethod();
        TThrowable excepFmx = dico.asException(this
                .referedType(node.getExpression().resolveTypeBinding(), (TNamedEntity) context.topType(), true));
        if (excepFmx != null) {
        	dico.createFamixThrownException(meth, excepFmx);
        }
        return super.visit(node);
    }

}
