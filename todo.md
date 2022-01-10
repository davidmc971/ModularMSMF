(A) Core Module +ModularMSMFCore
  (A) API Overview +ModularMSMFCoreAPI
    (F) - review files and rewrite if neccessary
  (A) Command Overview +ModularMSMFCore +CoreCommands
    (B) - report command:
      - write bug report into file/db #important
      - write player report into file/db #important
      - write other reports into file/db #important
      - remove bug report when done per command #new
      - remove player report when done per command #new
      - look over the command and rewrite if neccessary #veryimportant
      - add importance level per report #new #veryimportant
    (C) - mmsmf command:
      - remove instances
      - abstract prefixes from ChatUtils.ChatFormat #important
      - change onCommand switch to args.length #important
      - look over the command and rewrite if neccessary #veryimportant
    (F) - serverinfo command:
      - look over the command and add functions
    (F) - playershell command:
      - look over the command and rewrite if neccessary
    (B) - list command:
      - rewrite whole command to atleast list players when online without brackets #important
      - change structure for better readability #important
    (F) - language command:
      - review and maybe change code
    (F) - configure command:
      - review and recode file #important #new
      - add structure for command #new
    (F) - clearchat command:
      - review command and maybe optimize if neccessary
  (A) File Configuration Overview +ModularMSMFCore +CoreFileConfiguration
    (A) - review files of core and rewrite if neccessary
      (A) - review files of configuration and rewrite if neccessary #important
      (A) - review files of data and rewrite if neccessary #important
      - review files of filesystem and rewrite if neccessary
      - review files of storage and rewrite if neccessary
      (A) - fix coreevents and change structure #important
    (A) - review files of util and rewrite if neccessary

(A) Basics Module +ModularMSMFBasics
  (A) - review all commands and rewrite if neccessary
    - commands to watch:
      - back
      - ban
      - channels
      - clearinventory
      - feed
      - fly
      - get
      - heal
      - healall
      - home
      - kick
      - kill
      - killall
      - killme
      - motd
      - mute
      - set
      - setspawn
      - slaughter
      - spawn
      - stats
      - teleport
  (A) - review all other file related and rewrite if neccessary




 COMMANDS:
 REPORT:
   /report <category> <args>
   categories: player, bug, other
   /report player <playername> <reason>
   /report bug <description>
   /report other <description>
   structure updated, needs work
 BAN:
   - console can ban (w/o permission)
   - can ban player on+offline (with permission)
   - TODO: banip not implemented yet
 FEED:
   - can feed yourself (with permission)
   - can feed other users (with permission)
   - cannot feed console (only player can be feeded)
   - console can feed everyone but not itself (w/o permissions)
 HEAL:
   - can heal a player
   - healing yourself
 KICK:
   - can kick a player
   - even with or without reason
   - broadcasting it worldwide on server
 MMSMF:
   - TODO: missing many functions, have to be added
 TELEPORT:
   - can teleport players
 UNBAN:
   - same as BAN
 ECO:
   - nothing really happened earlier, so fixing commands

 MONEY:
  PROGRESS: (same as ECO)

 HOME:
   - only displaying messages and permissions has been set
   - no playerdata atm
   
 MUTE / UNMUTE: 
   
 WORTH: 
    
 GETSERVER:
   
 MOTD:
}


GENERAL TODO AND IDEAS:
  Languages: 
   might convert "languages.*" into seperate files to create which can be accessed outside which the user can edit to its own?
   like we dont need this anymore, creating a basic file which the user can edit at it's own will? option should be switchable
   in the console or as player in it's OP-state. like "/changelang"?
   TODO: Changing language for each player? {cm:2021-03-31}
   TODO: /language set//get
   
  Debugging-Info:
   like "/debug" which only the real dev's can see? all information should be displayed for console and for ingame real dev's.
   if anyone's getting trouble using our plugin so they can contact us via ingame chat using "/devhelp <message>" which could
   create a file or sending a message to our discord server throught a bot?
  
  LannguageManager.java:
   Adding client-side language setting? hard to code, but possible anyways. server should look up where's the user's coming from 
   so it could auto set it to it's most usable language? 

  Home:
   TODO Implementing hasNoHome (delhome, home)
   TODO Implementing hasHome (home, delhome)
   TODO Implementing hasAlreadyHome (sethome)
   TODO Implementing changeLocHome (sethome)
   TODO Implementing changeLocHomeOtherPlayer (sethome)
  All cmds:
   TODO adjusting all permissions and all language-linkings in all cmds (still working on it) {cm:2021-03-31}
   
  AbstractCommand
   now the basis for every Command, in the package commands, every command will be loaded automatically
   TODO: at the moment it has to be in plugin.yml to be executable, could be automated

 System of interfacing with players and console:
  TODO LanguageManager
  TODO ChatUtils
 
 Internal data management:
  DataManager
   -> switch mostly from yml to json, but still keep support
  TODO PermissionManager
  
 General utilities:
  Utils
  Interface für Einteilung zw. Spigot, Bukkit und Forge und dass wir die Daten auf gleiche Art und Weise bekommen können
  Feature: Annotation wie Forge only/Spigot only/Bukkit only
  TODO settings.yml mit Währungseinstellung
  TODO loading currency while set in settings.yml like $ or €
  TODO Filtering bad words? Wäre eine sehr gute Idee für alle die, die nicht unbedingt beleidigungen oder böse wörter im chat haben wollen.
  Verwarnsystem? Textchat-farbe des jenigen ändert sich auf gelb wenn einmal verwarnt, beim zweiten mal rot und beim dritten mal fliegt die person?
```
