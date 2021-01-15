import java.io.*;

import java.io.FileInputStream;
import java.io.IOException;


    /**
     * 自定义类加载器
     */
    public class ClassLoaderTest extends ClassLoader {

        private String path;

        public ClassLoaderTest(String path) {
            this.path = path;
        }

        @Override
        protected Class<?> findClass(String name)  {
            String classPath = path+name+".class";
            InputStream inputStream = null;
            ByteArrayOutputStream outputStream = null;
            try {
                inputStream = new FileInputStream(classPath);
                outputStream = new ByteArrayOutputStream();
                int temp = 0;
                while((temp = inputStream.read()) != -1){
                    outputStream.write(temp);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                try {
                    outputStream.close();
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            byte[] bytes = outputStream.toByteArray();
            return defineClass(name,bytes,0,bytes.length);
        }

        public static void main(String[] args) {
            ClassLoaderTest myClassLoader = new ClassLoaderTest("D:\\HomeWork\\Hello\\");
            try {
                Class clazz = myClassLoader.findClass("Hello");
                System.out.println(clazz);
                System.out.println(clazz.getConstructor().newInstance());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
