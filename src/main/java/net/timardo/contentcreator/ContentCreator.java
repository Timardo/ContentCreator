package net.timardo.contentcreator;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.*;
import net.timardo.contentcreator.capabilities.CapabilityWrapper;
import net.timardo.contentcreator.capabilities.CapabilityWrapperStorage;
import net.timardo.contentcreator.capabilities.ICapabilityWrapper;
import net.timardo.contentcreator.event.BasicEventHandler;
import net.timardo.contentcreator.loader.ContentLoaderManager;
import net.timardo.contentcreator.loader.LibClassLoader;

import java.io.File;

import org.apache.logging.log4j.Logger;

@Mod(modid = ContentCreator.MOD_ID, name = ContentCreator.NAME, version = ContentCreator.VERSION)
public class ContentCreator {
    public static final String MOD_ID = "contentcreator";
    public static final String NAME = "ContentCreator";
    public static final String VERSION = "0.2";
    public static final LibClassLoader LOADER = new LibClassLoader(ContentCreator.class.getClassLoader());
    
    public static ContentLoaderManager loaderManager;
    public static Logger logger;

    @EventHandler
    public void preInit(FMLPreInitializationEvent e) {
        logger = e.getModLog();
        Util.configDir = new File(e.getModConfigurationDirectory().getAbsolutePath() + "/contentcreator");
        Util.contentLibsDir = new File(Util.configDir.getAbsolutePath() + "/addons");
        CapabilityManager.INSTANCE.register(ICapabilityWrapper.class, new CapabilityWrapperStorage(), CapabilityWrapper::new);
        
        if (!Util.contentLibsDir.exists())
            Util.contentLibsDir.mkdirs();
        
        MinecraftForge.EVENT_BUS.register(new BasicEventHandler());
        loaderManager = new ContentLoaderManager();
        loaderManager.preLoadPresentAddons();
        loaderManager.postFMLEvent(e);
    }
    
    @EventHandler
    public void fml(FMLServerStartingEvent e) {
        loaderManager.postFMLEvent(e);
        e.registerServerCommand(new LoadCommand());
        loaderManager.registerCommands();
    }
    
    @EventHandler
    public void fml(FMLServerAboutToStartEvent e) {
        loaderManager.postFMLEvent(e);
    }
    
    @EventHandler
    public void fml(FMLServerStartedEvent e) {
        loaderManager.postFMLEvent(e);
    }
    
    @EventHandler
    public void fml(FMLServerStoppedEvent e) {
        loaderManager.postFMLEvent(e);
    }
    
    @EventHandler
    public void fml(FMLServerStoppingEvent e) {
        loaderManager.postFMLEvent(e);
    }
}
