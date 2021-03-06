package elec332.core.module;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import elec332.core.api.module.IModuleController;
import elec332.core.api.module.IModuleInfo;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.versioning.ArtifactVersion;

import javax.annotation.Nonnull;
import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 * Created by Elec332 on 8-10-2016.
 */
public class DefaultModuleInfo implements IModuleInfo {

    public DefaultModuleInfo(String owner, String name, String modDeps, String moduleDeps, boolean ADRIM, boolean alwaysOn, String mainClazz, IModuleController moduleController){
        this.owner = requireNonNull(owner);
        this.name = requireNonNull(name);
        this.modDeps = IModuleInfo.parseDependencyInfo(modDeps);
        this.moduleDeps = Strings.isNullOrEmpty(moduleDeps) ? ImmutableList.of() : ImmutableList.copyOf(Lists.newArrayList(moduleDeps.split(";")));
        this.autoDIRNM = ADRIM;
        this.alwaysEnabled = alwaysOn;
        this.clazz = mainClazz;
        this.moduleController = moduleController;
        this.combinedName = new ResourceLocation(this.owner, this.name.toLowerCase());
    }

    private final String owner, name, clazz;
    private final ResourceLocation combinedName;
    private final boolean autoDIRNM, alwaysEnabled;
    private final List<ArtifactVersion> modDeps;
    private final List<String> moduleDeps;
    private final IModuleController moduleController;


    @Nonnull
    @Override
    public String getOwner() {
        return owner;
    }

    @Nonnull
    @Override
    public String getName() {
        return name;
    }

    @Nonnull
    @Override
    public ResourceLocation getCombinedName() {
        return combinedName;
    }

    @Override
    public boolean autoDisableIfRequirementsNotMet() {
        return autoDIRNM;
    }

    @Nonnull
    @Override
    public List<ArtifactVersion> getModDependencies() {
        return modDeps;
    }

    @Nonnull
    @Override
    public List<String> getModuleDependencies() {
        return moduleDeps;
    }

    @Nonnull
    @Override
    public String getModuleClass() {
        return clazz;
    }

    @Override
    public boolean alwaysEnabled() {
        return alwaysEnabled;
    }

    @Nonnull
    @Override
    public IModuleController getModuleController() {
        return moduleController;
    }

}
