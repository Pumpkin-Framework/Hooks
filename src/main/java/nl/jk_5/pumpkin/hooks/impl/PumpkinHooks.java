package nl.jk_5.pumpkin.hooks.impl;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.spongepowered.api.Game;
import org.spongepowered.api.event.Subscribe;
import org.spongepowered.api.event.state.PreInitializationEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.service.ProviderExistsException;

import nl.jk_5.pumpkin.hooks.PumpkinHooksService;

@Plugin(id = "pumpkin-hooks", name = "Pumpkin Hooks")
public class PumpkinHooks {

    @Inject
    private Game game;

    @Inject
    private Logger logger;

    private PumpkinHooksService impl;

    @Subscribe
    public void init(PreInitializationEvent event){
        this.impl = new PumpkinHooksServiceImpl();

        try{
            game.getServiceManager().setProvider(this, PumpkinHooksService.class, this.impl);
        }catch(ProviderExistsException e){
            logger.warn("Was not able to register hooks implementation because it already exists");
        }
    }
}
