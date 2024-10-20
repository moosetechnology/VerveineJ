package inner;

import badetitou.Canard;
import badetitou.Patate;
import badetitou.Click;

public class InnerClass {

    /**
     * We are testing that String as a reference
     */
    private void methodWithInstanceOf() {
        Object a = "hello";
        hello(a instanceof String);
    }

    private void hello(boolean test){

    }

    @Override
    public void deletePatch(PatchDTOGWT patch) throws SeditGwtException {
        try {
            PatchDTO patchModel = (PatchDTO) mapper.cloneToModel(patch,
                    PatchDTO.class);
            ucPatch.deletePatch(patchModel);
        } catch (SeditException se) {
            throw new SeditGwtException(
                    se instanceof BusinessException ? SeditGwtException.TYPE_BUSINESS
                            : SeditGwtException.TYPE_TECHNICAL, se);
        } catch (Throwable t) {
            throw new SeditGwtException(SeditGwtException.TYPE_INCONNUE, t);
        }
    }

}