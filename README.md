# This is an archived project.

# ContentCreator

I just didn't find any mod that allows you to dynamically create your very own addons (some would say plugins) to minecraft

Who am I to reload whole 150-mod modpack every single time I want to add or test a new command or bind new event

This mod is still very raw, but it is working for basic stuff like loading your addon and calling its initialization method, hooking onto events and registering commands

## Scope of this project

The main scope is to provide server owners ability to create their own "plugins" using forge. Plugins which can be loaded/unloaded at any time without the need of restarting whole server. It is also intended for custom modpack creators to create their very own behavioral content packs reacting to events without creating another mod just for this purpose, because debugging mods in large modpacks is just pain in the ass.

Also one of the features that will be added in future is to send addons to client while playing on server to make the server a bit more lighter from the load of calculating everything (visual-wise). This will unleash the power needed for another mod/addon I am working on, which still needs a lot of work :) (not-only-visual-wise)

## How to create an addon

Check out the [wiki](https://github.com/Timardo/ContentCreator/wiki)!

## How to load an addon

Currently there is only one way. Put an addon to folder `config/contentcreator/addons` and type command `/load <full_name_of_file>`. Don't forget the extension! Example: `/load My-Addon.jar`

more to come..