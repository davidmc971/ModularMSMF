load order:
 - ModularMSMF-Core
   - Instantiate commands
   - Register commands and inject them via reflection
 - Modules
   - Register module in Core
   - Instantiate commands
   - Register commands through Core