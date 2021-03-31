package io.github.davidmc971.modularmsmf.api;

/**
 * All main classes of ModularMSMF Modules must implement this to register in
 * ModularMSMF-Core.
 * 
 * @author David Alexander Pfeiffer (davidmc971)
 * @since 0.3.0
 */
public interface IModule {
    /**
     * Name of the module, is used for example while retrieving storage handles.
     * 
     * @return The module name.
     */
    public String Name();

    /**
     * Array containing all commands from this module. Used for registering commands
     * at runtime. Can be empty if there is no commands.
     * 
     * @return Array of the module's commands.
     */
    public IModularMSMFCommand[] Commands();
}
