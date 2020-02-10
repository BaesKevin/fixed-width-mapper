package be.kevinbaes.encoded_string_mapper.processor;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.util.Set;

@SupportedAnnotationTypes({"be.kevinbaes.encoded_string_mapper.annotation.StringEncodedValue"})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class StringEncodedEntityProcessor extends AbstractProcessor {

  @Override
  public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
    processingEnv.getMessager().printMessage(Diagnostic.Kind.MANDATORY_WARNING, "encoded string mapper processor running!");
    return false;
  }
}
