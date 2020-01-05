package net.timardo.contentcreator;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

import net.minecraft.command.ICommandSender;
import net.timardo.contentcreator.loader.AddonLoadingException;

public class Util {
    
    public static File configDir;
    public static File contentLibsDir;

    public static String getJarDir()
    {
        return contentLibsDir.getAbsolutePath();
    }

    public static void loadAddon(String file, ICommandSender sender, ClassLoader cl) throws AddonLoadingException {
        File actualFile = new File(Util.getJarDir() + "/" + file);
        
        if (!actualFile.exists())
            throw new AddonLoadingException("File '" + actualFile.getAbsolutePath() + "' doesn't exist, aborting!");
        
        JarFile jar = null;
        InputStream inputStream = null;
        String mainClass = null;

        try {
            jar = new JarFile(actualFile);
            JarEntry entry = jar.getJarEntry("main.cl");

            if (entry == null) {
                throw new AddonLoadingException("Jar does not contain main.cl");
            }

            inputStream = jar.getInputStream(entry);
            mainClass = IOUtils.toString(inputStream, StandardCharsets.UTF_8.name());

        }
        
        catch (IOException ex) {
            ContentCreator.logger.warn("Could not load main class from main.cl file in '" + actualFile.getName() + "'\n More info: " + ExceptionUtils.getStackTrace(ex));
            throw new AddonLoadingException("Could not load main class from main.cl file in '" + actualFile.getName() + "'\n More info in console");
        }
        
        finally {
            if (jar != null) {
                try {
                    jar.close();
                }
                
                catch (IOException e) {}
            }
            
            if (inputStream != null) {
                try {
                    inputStream.close();
                }
                
                catch (IOException e) {}
            }
        }
        
        try {
            if (mainClass == null) {
                throw new AddonLoadingException("Could not load main class from main.cl file in '" + actualFile.getName() + "'");
            }
                
            ContentCreator.LOADER.loadFile(actualFile, mainClass, sender);
        }
        
        catch (MalformedURLException mue) {
            ContentCreator.logger.warn("File path provided '" + actualFile.getAbsolutePath() + "' threw an exception (MalformedURLException)\n More info: " + ExceptionUtils.getStackTrace(mue));
            throw new AddonLoadingException("File path provided threw an exception (MalformedURLException). More info in console.");
        }
        
    }
    
}
