using System;

namespace verse.rules {
	public interface IRules {
		string Execute(string Path, string SettingsSection);
	}
}
