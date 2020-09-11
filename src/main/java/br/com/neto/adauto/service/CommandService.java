package br.com.neto.adauto.service;

import br.com.neto.adauto.exception.ApplicationException;
import br.com.neto.adauto.model.enumeration.EnumCommands;

public class CommandService {

    private RouterService routerService;

    private EmitterService emitterService;

    public CommandService() {
        this.routerService = new RouterService();
        this.emitterService = new EmitterService();
    }

    public void executeCommand(String commandString) {
        try {
            EnumCommands command = identifyCommand(commandString);
            if (command == null) {
                showHelp();
            } else {
                switch (command) {
                    case CREATE_ROUTER:
                        if (commandString.contains(EnumCommands.CREATE_ROUTER.getComando())) {
                            String data = commandString.replace(EnumCommands.CREATE_ROUTER.getComando() + " ", "");
                            routerService.createRouterCommand(data);
                        } else {
                            routerService.createRouter();
                        }
                        break;
                    
                    case ROUTER_DETAIL:
                        String[] parts = commandString.split(" ");
                        if (parts.length > 1) {
                            routerService.routerDetail(Integer.valueOf(parts[1]));
                        } else {
                            routerService.routerDetail(null);
                        }
                        break;
                        
                    case STOP_ROUTER:
                        parts = commandString.split(" ");
                        if (parts.length > 1) {
                            routerService.stopRouter(Integer.valueOf(parts[1]));
                        } else {
                            routerService.stopRouter(null);
                        }
                        break;
                        
                    case ROUTER_LIST:
                        routerService.routerList();
                        break;
                        
                    case EMIT_MESSAGE:
                        if (commandString.contains(EnumCommands.EMIT_MESSAGE.getComando())) {
                            String data = commandString.replace(EnumCommands.EMIT_MESSAGE.getComando() + " ", "");
                            emitterService.emitCommand(data);
                        } else {
                            emitterService.emit();
                        }
                        break;
                        
                    case ROUTER_LIST_DETAIL:
                        routerService.routerListDetail();
                        break;
                        
                    case HELP:
                        showHelp();
                        break;
                }
            }
        } catch (ApplicationException e) {
            e.printStackTrace();
        }
    }

    private EnumCommands identifyCommand(String command) {
        if (command.length() == 1) {
            Integer index = Integer.valueOf(command);
            return getCommandByIndex(index);
        } else {
            try {
                String[] commandParts = command.split(" ");
                if (commandParts[0].length() == 1) {
                    return getCommandByIndex(Integer.valueOf(commandParts[0]));
                }
                return getCommandByName(commandParts[0]);
            } catch (IllegalArgumentException e) {
                return null;
            }
        }
    }

    private EnumCommands getCommandByIndex(Integer index) {
        for (EnumCommands enumCommands : EnumCommands.values()) {
            if (enumCommands.getIndex().equals(index)) {
                return enumCommands;
            }
        }
        return null;
    }

    private EnumCommands getCommandByName(String name) {
        for (EnumCommands enumCommands : EnumCommands.values()) {
            if (enumCommands.getComando().toUpperCase().equals(name.toUpperCase())) {
                return enumCommands;
            }
        }
        return null;
    }

    public void showHelp() {
        String leftAlignFormat = "| %-7s | %-38s |%n";
        System.out.format("+---------+----------------------------------------+%n");
        System.out.format("| Comando | Nome                                   |%n");
        System.out.format("+---------+----------------------------------------+%n");
        for (EnumCommands enumCommand : EnumCommands.values()) {
            System.out.format(leftAlignFormat, enumCommand.getIndex(), enumCommand.getNome());
        }
        System.out.format("+---------+----------------------------------------+%n");
    }
}
