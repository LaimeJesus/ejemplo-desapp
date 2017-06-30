package architecture;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.reflections.Reflections;
import org.reflections.scanners.MemberUsageScanner;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import builders.UserBuilder;
import model.users.User;
import services.general.GeneralService;
import services.microservices.GenericService;
import services.microservices.ProductService;
import util.Password;
import util.Permission;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath*:/META-INF/spring-persistence-context.xml", "classpath*:/META-INF/spring-services-context.xml" })
public class ArchitectureTest {

  @Autowired
  @Qualifier("services.general.generalservice")
  private GeneralService generalService;
  
  @Test
  public void testTransactional() {

    generalService.getUserService().deleteAll();

    User someUser = new UserBuilder()
        .withEmail("transactionaltest@gmail.com")
        .withPassword(new Password("password"))
        .withUsername("transactional")
        .withUserPermission(Permission.ADMIN)
        .build();

    try {
      generalService.createUserForTest(someUser);
    } catch (Exception e) {
    } finally {
      Assert.assertTrue(generalService.getUserService().retriveAll().isEmpty());
    }
  }

  //Check public methods in services are transactional
  @Test
  public void testArchiteture1() {
    boolean result = true;

    try {

      Method[] methods = Class.forName("services.general.GeneralService").getMethods();
      Method[] superMethods = Class.forName("services.general.GeneralService").getSuperclass().getMethods();

      List<Method> myMethods = Arrays.asList(methods);
      List<Method> myFatherMethods = Arrays.asList(superMethods);

      for (Method m : methods) {
        if ( !(m.getName().contains("Service") || myFatherMethods.contains(m)) ) {
          Annotation[] annotations = m.getAnnotations();
          if (annotations.length == 0) {
            result = false;
          }
          for (Annotation a : annotations) {
            result &= a.toString().startsWith("@org.springframework.transaction.annotation.Transactional");
          }
        }
      }

      Assert.assertTrue(result);

    } catch (SecurityException | ClassNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      Assert.fail();
    }
  }

  
  //check controllers methods returns response
  //@Test
  public void testArchiteture2() {

    boolean result = false;

    try {
      List<Method> mymethods = Arrays.asList(Class.forName("rest.controllers.UsersController").getMethods());
      List<Method> myFatherMethods = Arrays.asList(Class.forName("rest.controllers.UsersController").getSuperclass().getMethods());

      for (Method m : mymethods) {

        if ( !(m.getName().contains("Service") || myFatherMethods.contains(m)) )

        {

          System.out.println(m.getName());
          List<Annotation> annotations = Arrays.asList(m.getAnnotations());

          if (annotations.isEmpty()){
            result = false;
          }

          for (Annotation a : annotations) {
            if (  a.toString().contains("GET")  ||
                a.toString().contains("POST")   ||
                a.toString().contains("PUT")  ||
                a.toString().contains("DELETE")
                ) {
              result &= m.getReturnType().getName().contains("javax.ws.rs.core.Response");
            }
          }
        }

      }

      Assert.assertTrue(result);

    } catch (SecurityException | ClassNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      Assert.fail();
    }

  }
  
  //check no subclasses from generic services have a print ln call
  @Test
  public void testArchitecture3() throws ClassNotFoundException, NoSuchFieldException, SecurityException, NoSuchMethodException{
    Reflections reflections = new Reflections(new ConfigurationBuilder()
        .setUrls((ClasspathHelper.forClass(GenericService.class)))
        .setScanners(new SubTypesScanner(true),new MemberUsageScanner()));
    
    Class<?> systemClass = java.lang.Class.forName("java.lang.System");
    Field outField = systemClass.getDeclaredField("out");
    Class<?> printStreamClass = outField.getType();
    Method printlnMethod = printStreamClass.getDeclaredMethod("println", String.class);
    
    Set<Member> sm = reflections.getMethodUsage(printlnMethod);
    for(Member m : sm){
      System.out.println(m.getDeclaringClass());
    }
    Assert.assertTrue(sm.isEmpty());
    
  }
  
  @Test
  public void testArchitecture4(){
    Reflections reflections = new Reflections(new ConfigurationBuilder()
        .setUrls((ClasspathHelper.forClass(GenericService.class)))
        .setScanners(new SubTypesScanner(true),new MethodAnnotationsScanner()));
    
    Set<Class<? extends GenericService>> subclasess = reflections.getSubTypesOf(GenericService.class);
    
    Boolean res = true;
    
    for(Class<? extends GenericService> subclass : subclasess){
      Method[] subclassMethods = subclass.getMethods();
      List<Method> superMethods = Arrays.asList(subclass.getSuperclass().getMethods());
      for(Method method : subclassMethods){
        if(!superMethods.contains(method)){
          if(Modifier.isPublic(method.getModifiers())){
            if((method.getReturnType() == (void.class) || !checkReturn(method.getReturnType()))){
                if(checkParameters(method.getParameterTypes())){             
                  res &= checkAnnotations(method);
                }
            }
          }
        }
      }
    }
    Assert.assertTrue(res);
  }
  
  private Boolean checkAnnotations(Method method) {
    boolean res = false;
//    res &= method.isAnnotationPresent(Transactional.class);
    for(Annotation an : method.getAnnotations()){
      if(an.toString().startsWith("@org.springframework.transaction.annotation.Transactional")){
        res = true;
        break;
      }
    }
    return res;
  }

  //check if return type is a subclass of generic service
  private boolean checkReturn(Class<?> returnType) {
    return GenericService.class.isAssignableFrom(returnType);
  }

  //checks if method's first argument is a subclass of generic service
  private boolean checkParameters(Class<?>[] parameterTypes) {
    if(parameterTypes.length == 1){
      return !GenericService.class.isAssignableFrom(parameterTypes[0]);
    }else{
      return true;
    }
  }
}
