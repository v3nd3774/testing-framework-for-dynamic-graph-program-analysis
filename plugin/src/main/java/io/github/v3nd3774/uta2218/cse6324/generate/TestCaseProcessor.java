package io.github.v3nd3774.uta2218.cse6324.generate;

import com.google.auto.service.AutoService;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.ExecutableType;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@SupportedAnnotationTypes("io.github.v3nd3774.uta2218.cse6324.generate.GraphTest")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@AutoService(Processor.class)
public class TestCaseProcessor  extends AbstractProcessor {

    public static void indetifyGraphTestAnnotation(Class clazz){

        for (Method method : clazz.getDeclaredMethods()) {
            if(method.isAnnotationPresent(GraphTest.class)){
                System.out.println("GraphTest Annotation found " + method.getName());
            }
        }
    }

    private void writeBuilderFile(String className, GraphTest graphTest) throws IOException {

        String packageName = null;
        int lastDot = className.lastIndexOf('.');
        if (lastDot > 0) {
            packageName = className.substring(0, lastDot);
        }

        String simpleClassName = className.substring(lastDot + 1);
        String builderClassName = className + "Test";
        String builderSimpleClassName = builderClassName.substring(lastDot + 1);

        JavaFileObject builderFile = processingEnv.getFiler().createSourceFile(builderClassName);
        try (PrintWriter out = new PrintWriter(builderFile.openWriter())) {

          /*  if (packageName != null) {
                out.print("package ");
                out.print("edu.uta.cse6324.team9.tradeconnect.service");
                out.println(";");
                out.println();
            }*/
            out.println("package edu.uta.cse6324.team9.tradeconnect.service;\n");
            out.print("import com.fasterxml.jackson.databind.ObjectMapper;\n");
            out.print("import edu.uta.cse6324.team9.tradeconnect.entity.Person;\n");
            out.print("import edu.uta.cse6324.team9.tradeconnect.repository.PersonRepository;\n");
            out.print("import edu.uta.cse6324.team9.tradeconnect.repository.TickerRepository;\n");
            out.print("import io.github.v3nd3774.uta2218.cse6324.generate.GraphMapper;\n");
            out.print("import io.github.v3nd3774.uta2218.cse6324.s001.DotLanguageFile;\n");
            out.print("import io.github.v3nd3774.uta2218.cse6324.s001.HashMapEdge;\n");
            out.print("import org.jgrapht.Graph;\n");
            out.print("import org.junit.jupiter.api.AfterEach;\n");
            out.print("import org.junit.jupiter.api.BeforeEach;\n");
            out.print("import org.junit.jupiter.api.Test;\n");
            out.print("import org.skyscreamer.jsonassert.JSONAssert;\n");
            out.print("import org.springframework.beans.factory.annotation.Autowired;\n");
            out.print("import org.springframework.boot.test.context.SpringBootTest;\n");
            out.print("import java.io.File;\n");
            out.print("import java.nio.file.Files;\n");
            out.print("import java.nio.file.Paths;\n");
            out.print("import java.util.HashMap;\n");

            out.print("\n@SpringBootTest\n");
                  out.println(  "class TradeServiceTest {\n");
            out.println("@Autowired TradeService tradeService;\n" +
                    "    @Autowired PersonRepository personRepository;\n" +
                    "    @Autowired TickerRepository tickerRepository;\n" +
                    "    @Autowired ObjectMapper objectMapper;\n");


            out.println("GraphMapper graphMapper;\n");
            out.println("@BeforeEach");
            out.println("void setUp() throws Exception {\n" +
                    "        File inputDOTFile = new File(this.getClass().getClassLoader().getResource(\"" +
                    //"one-purchase.dot" +
                            graphTest.init() +
                    "\").getFile());\n" +
                    "        DotLanguageFile fileC = new DotLanguageFile(inputDOTFile);\n" +
                    "        Graph<HashMap<String, String>, HashMapEdge> graph = fileC.parse();\n" +
                    "        GraphMapper graphMapper = new GraphMapper();\n" +
                    "        tradeService.saveAllPersons(graphMapper.parseValue(graph, Person.class));\n" +
                    "    }\n");
            out.println("@Test");
            out.println("void savePerson() throws Exception {\n" +
                    "        Person person = objectMapper.readValue(this.getClass().getClassLoader().getResourceAsStream(\"" +
                    //"person_input.json" +
                            graphTest.inbound()[0] +
                    "\"), Person.class);\n" +
                    "        Person person1 = tradeService.savePerson(person);\n" +
                    "\n" +
                    "        String readString = Files.readString(Paths.get(this.getClass().getClassLoader().getResource(\"" +
                    //"person_output.json" +
                            graphTest.outbound()[0] +
                    "\").toURI()));\n" +
                    "        JSONAssert.assertEquals(readString, objectMapper.writeValueAsString(person1), false);\n" +
                    "    }\n");
            out.println("@AfterEach");
            out.println("void tearDown() {\n" +
                    "        personRepository.deleteAll();\n" +
                    "        tickerRepository.deleteAll();\n" +
                    "    }");
            out.println("}");



        }
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        try {
            GraphTest graphTest = null;
            //writeBuilderFile("Test123");
            for (TypeElement annotation : annotations) {

                Set<? extends Element> annotatedElements = roundEnv.getElementsAnnotatedWith(annotation);
                System.out.println("annotation: " + annotation);
                System.out.println("annotatedElement: " + annotatedElements);

                Map<Boolean, List<Element>> annotatedMethods = annotatedElements.stream().collect(Collectors.partitioningBy(element -> ((ExecutableType) element.asType()).getParameterTypes().size() == 1 ));
                System.out.println("annotatedMethods: " + annotatedMethods);

                List<Element> setters = annotatedMethods.get(true);
                List<Element> otherMethods = annotatedMethods.get(false);

                System.out.println("setters: " + setters);
                graphTest = setters.get(0).getAnnotation(GraphTest.class);
                System.out.println("graphTest.init()" + graphTest.init());
                System.out.println("graphTest.inbound()" + graphTest.inbound()[0]);
                System.out.println("graphTest.outbound()" + graphTest.outbound()[0]);


                // otherMethods.forEach(element -> processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "@BuilderProperty must be applied to a setXxx method with a single argument", element));



                //String className = ((TypeElement) setters.get(0).getEnclosingElement()).getQualifiedName().toString();

               // Map<String, String> setterMap = setters.stream().collect(Collectors.toMap(setter -> setter.getSimpleName().toString(), setter -> ((ExecutableType) setter.asType()).getParameterTypes().get(0).toString()));

            }

                writeBuilderFile("TradeService", graphTest);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }
}
