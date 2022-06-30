# ForgeTemplate

A simple template based off of the [MDK](https://github.com/MinecraftForge/MinecraftForge/tree/1.17.x/mdk) that I use to create new projects quicker by extracting all mod-related info to `gradle.properties`.
**This does not do the work for you.** It only makes the initial Gradle setup easier and less tedious.

## Setting up
* Open up `gradle.properties` and change all the necessary properties
* Rename the main package to be the same as `mod_base_package` in the properties file, e.g. `me.sizableshrimp.nyfscalendar`

### Using mixins?
#### NOTE: Mixin is properly shipped in Forge 1.18 as of `38.0.6`. Make sure to set this as the minimum version in `forge_version_range` if you use Mixins!
* Delete `build.gradle`
* Rename `mixinbuild.gradle` to `build.gradle`
* Rename `nyfscalendar.mixins.json` to start with your mod id
* Add mixins under the `mixin` package inside your main package, e.g. `me.sizableshrimp.nyfscalendar.mixin`

### Not using mixins?
* Delete `mixinbuild.gradle`
* Delete `src/main/resources/nyfscalendar.mixins.json`
* Remove `mixin_version` from `gradle.properties`

Finally, import into your IDE of choice.