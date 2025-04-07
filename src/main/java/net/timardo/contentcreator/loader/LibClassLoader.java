package net.timardo.contentcreator.loader;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import org.apache.commons.lang3.exception.ExceptionUtils;

import net.minecraft.command.ICommandSender;
import net.minecraft.launchwrapper.LaunchClassLoader;
import net.timardo.contentcreator.ContentCreator;

public class LibClassLoader extends URLClassLoader {
    
    private LaunchClassLoader parentCL;
    
    public LibClassLoader(ClassLoader parentLoader) {
        super(new URL[0], parentLoader); // this is how forge loads mods, I have no idea why this specific approach of loading jar files works
        parentCL = (LaunchClassLoader) parentLoader;
    }
    
    public void loadFile(File libFile, String main, ICommandSender commandSender) throws MalformedURLException, AddonLoadingException {
        Class<?> mainClass;
        IContentAddon addon = null;
        parentCL.addURL(libFile.toURI().toURL());
        
        try {
            mainClass = Class.forName(main, true, parentCL);
            
            try {
                addon = (IContentAddon) mainClass.newInstance();
                String addonID = addon.getAddonID();
                
                if (ContentCreator.loaderManager.addons.contains(addonID)) {
                    throw new AddonLoadingException("Addon '" + addonID + "' is already loaded!");
                }
                
                ContentCreator.loaderManager.addons.add(addon.getAddonID()); //add before loading to prevent half loading without noticing while catching an exception
                addon.load(commandSender, ContentCreator.loaderManager);
            }
            
            catch (ClassCastException cce) {
                ContentCreator.logger.warn("Main class '" + mainClass + "' in lib '" + libFile.getName() + "' doesn't implement required IContentAddon class!");
            }
            
            catch (InstantiationException ie) {
                ContentCreator.logger.warn("Main class '" + mainClass + "' in lib '" + libFile.getName() + "' could not be instatiated!");
            }
            
            catch (IllegalAccessException iae) {
                ContentCreator.logger.warn("Main class '" + mainClass + "' in lib '" + libFile.getName() + "' is inaccessible!");
            }
            
            catch (AddonLoadingException ale) {
                throw ale;
            }
            
            catch (Exception e) {
                ContentCreator.logger.warn("Unknow exception has been caught while loading '" + libFile.getName() + "'. More info: " + ExceptionUtils.getStackTrace(e));
                throw new AddonLoadingException("Unknow exception has been caught while loading '" + libFile.getName() + "'. More info in console");
            }
            
        }
        
        catch (ClassNotFoundException e) {
            ContentCreator.logger.warn("No main class '" + main + "' defined in main.cl found in lib '" + libFile.getName() + "'!");
        }
    }
}
