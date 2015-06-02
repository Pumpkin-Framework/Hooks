package nl.jk_5.pumpkin.hooks.impl;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import net.minecraftforge.fml.common.DummyModContainer;
import net.minecraftforge.fml.common.LoadController;
import net.minecraftforge.fml.common.ModMetadata;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.versioning.ArtifactVersion;
import net.minecraftforge.fml.common.versioning.VersionParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.service.ProviderExistsException;
import org.spongepowered.common.Sponge;

import nl.jk_5.pumpkin.hooks.PumpkinHooksService;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import javax.annotation.Nonnull;

@SuppressWarnings("unused")
public class PumpkinHooks extends DummyModContainer implements PluginContainer {

    private static final String MODID = "pumpkin-hooks";
    private static final Logger logger = LoggerFactory.getLogger("Pumpkin-Hooks");
    private static final List<ArtifactVersion> dependencies = ImmutableList.of(VersionParser.parseVersionReference("pumpkin"));
    private static final Set<ArtifactVersion> requirements = ImmutableSet.of(VersionParser.parseVersionReference("pumpkin"));

    private PumpkinHooksService impl;

    @SuppressWarnings("unused")
    public PumpkinHooks(ModMetadata md) {
        super(new ModMetadata());
        md = getMetadata();
        md.modId = "pumpkin-hooks";
        md.name = "Pumpkin Hooks";
        md.version = PumpkinHooks.class.getPackage().getImplementationVersion();
        md.authorList = Collections.singletonList("jk-5");
        md.description = "Additional hooks in vanilla code for pumpkin so we can extend our functionality beyond what sponge provides us";
        md.url="https://pumpkin.jk-5.nl";
    }

    @Override
    public boolean registerBus(EventBus bus, LoadController controller) {
        bus.register(this);
        return true;
    }

    @Subscribe
    public void init(FMLPreInitializationEvent event){
        this.impl = new PumpkinHooksServiceImpl();

        try{
            Sponge.getGame().getServiceManager().setProvider(this, PumpkinHooksService.class, this.impl);
        }catch(ProviderExistsException e){
            logger.warn("Was not able to register hooks implementation because it already exists");
        }
    }

    @Override
    @Nonnull
    public String getId() {
        return MODID;
    }

    @Override
    @Nonnull
    public Object getInstance() {
        return this;
    }

    @Override
    public List<ArtifactVersion> getDependencies() {
        return dependencies;
    }

    @Override
    public Set<ArtifactVersion> getRequirements() {
        return requirements;
    }
}
