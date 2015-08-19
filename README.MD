# Rosetta

Rosetta is a localization API for Bukkit plugins.

### Usage

Rosetta is a library, and therefore must be shaded or otherwise included with your plugin to work properly. Once that's
done, you can initialize the locale manager in your `onEnable()` like so:

```
LocaleManager localeManager = new LocaleManager(this);
```

The rest should be sufficiently explained through the documentation (published continuously) at
[http://jd.caseif.net/rosetta](http://jd.caseif.net/rosetta).

To define locales, simply create a `lang` folder in your JAR and insert a `.properties` file for each locale you wish to
define. For example, `en_US.properties`, `de_DE.properties`, etc. These will be loaded by Rosetta and all contained keys
will be accessible through the `LocaleManager` instance you created.

Additionally, users of your plugin may define their own locales in a similar way, placing the `lang` folder in the data
folder of the plugin. *Note: Locales defined in this way will override those shipped with the JAR if any duplicate keys are
found.*

Placeholder sequences are a powerful tool provided by Rosetta, and allow for locale messages to be customized at
runtime. A message with placeholder sequences might look something like:

```
arena.win=%1 has won in arena %2!
```

This would be loaded as follows:

```
Localizable message = localeManager.getLocalizable("arena.win");
```

Then, it would be customized like:

```
// String winner = ...
// String arena = ...
message.withReplacements(winner, arena);
```

Now, when the message is localized, the replacement strings will be incorporated into the output string. Because
`withReplacements` returns the object it is called upon, it is also possible to chain it with a `sendTo` call and still
have the replacement strings incorporated.

### Prerequisites

Rosetta requires Java 7 for compilation and use.

### Building

Rosetta uses Gradle as its build system. To clone and build the project from scratch:

- `git clone https://github.com/caseif/Rosetta.git`
- `cd Rosetta`
- `cp scripts/* .git/hooks`
- `./gradlew` (`gradlew` or `sh ./gradlew` on Windows)

### Contact

I can be contacted [via email at me@caseif.net](mailto:me@caseif.net).

### Copyright

Rosetta's source code and binary are provided under the BSD 3-Clause license.
