package com.zorbeytorunoglu.ultimatebot.permissions;

import com.zorbeytorunoglu.ultimatebot.configuration.Configuration;
import com.zorbeytorunoglu.ultimatebot.configuration.ConfigurationProvider;
import com.zorbeytorunoglu.ultimatebot.configuration.Resource;
import com.zorbeytorunoglu.ultimatebot.configuration.YamlConfiguration;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class PermissionHandler {

    private HashMap<String, List<String>> userPermissions=new HashMap<>();
    private HashMap<String, List<String>> rolePermissions=new HashMap<>();

    private final Resource permissionResource;

    private final Configuration configuration;

    public PermissionHandler(Resource permissionsResource) {
        this.permissionResource=permissionsResource;

        try {
            this.configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(permissionsResource.getFile());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for (String command:configuration.getSection("commands").getKeys()) {
            userPermissions.put(command, configuration.getStringList("commands."+command+".users"));
            rolePermissions.put(command, configuration.getStringList("commands."+command+".roles"));
        }

    }

    public HashMap<String, List<String>> getUserPermissions() {
        return userPermissions;
    }

    public HashMap<String, List<String>> getRolePermissions() {
        return rolePermissions;
    }

    public Resource getPermissionResource() {
        return permissionResource;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public void setRolePermissions(HashMap<String, List<String>> rolePermissions) {
        this.rolePermissions = rolePermissions;
    }

    public void setUserPermissions(HashMap<String, List<String>> userPermissions) {
        this.userPermissions = userPermissions;
    }

    public boolean hasPermission(String command, User user) {
        return userPermissions.containsKey(command) && userPermissions.get(command).contains(user.getId());
    }

    public boolean hasPermission(String permission, Member member) {
        if (hasPermission(permission, member.getUser())) return true;
        if (member.isOwner()) return true;
        if (rolePermissions.containsKey(permission)) {
            if (rolePermissions.get(permission).contains("everyone")) return true;
            if (!member.getRoles().isEmpty()) {
                for (Role role:member.getRoles()) {
                    return hasPermission(permission,role);
                }
                return false;
            }
        }
        return false;
    }

    public boolean hasPermission(String permission, Role role) {
        if (rolePermissions.containsKey(permission)) return rolePermissions.get(permission).contains(role.getId()) ||
                rolePermissions.get(permission).contains("everyone");
        return false;
    }

    public void saveFile() {
        try {
            new YamlConfiguration().save(configuration,permissionResource.getFile());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
