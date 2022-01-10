## Load order:
 - ModularMSMF-Core
   - Instantiate commands
   - Register commands and inject them via reflection
 - Modules
   - Register module in Core
   - Instantiate commands
   - Register commands through Core

## Storage Layout:
- ModularMSMF/
  - defaults/
    - languages/
      - de_DE.yml
      - en_US.yml
    - userdata.yml
    - settings.yml
  - languages/
    - de_DE.yml (optional)
    - en_US.yml (optional)
  - userdata/
    - [uuid].yml
  - settings.yml