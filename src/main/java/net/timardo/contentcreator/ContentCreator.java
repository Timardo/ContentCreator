package net.timardo.contentcreator;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.timardo.contentcreator.loader.ContentLoaderManager;
import net.timardo.contentcreator.loader.LibClassLoader;

import java.io.File;

import org.apache.logging.log4j.Logger;

@Mod(modid = ContentCreator.MOD_ID, name = ContentCreator.NAME, version = ContentCreator.VERSION)
public class ContentCreator {
    public static final String MOD_ID = "contentcreator";
    public static final String NAME = "ContentCreator";
    public static final String VERSION = "0.1";
    public static final LibClassLoader LOADER = new LibClassLoader(ContentCreator.class.getClassLoader());
    
    public static ContentLoaderManager loaderManager;
    public static Logger logger;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        Util.configDir = new File(event.getModConfigurationDirectory().getAbsolutePath() + "/contentcreator");
        Util.contentLibsDir = new File(Util.configDir.getAbsolutePath() + "/addons");
        
        if (!Util.contentLibsDir.exists())
            Util.contentLibsDir.mkdirs();
        
        loaderManager = new ContentLoaderManager();
    }
    
    @EventHandler
    public void startup(FMLServerStartingEvent e) {
        e.registerServerCommand(new LoadCommand());
    }
}
