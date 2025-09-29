// Shoelace component loader (Web Awesome!)
import { setBasePath } from '@shoelace-style/shoelace/dist/utilities/base-path.js';
// Provide a base path if you later copy assets (icons, fonts) under /shoelace
// setBasePath('/shoelace');

// Theme (light). You can also import dark.css and toggle with a class.
import '@shoelace-style/shoelace/dist/themes/light.css';

// Components actually used
import '@shoelace-style/shoelace/dist/components/button/button.js';
import '@shoelace-style/shoelace/dist/components/input/input.js';
import '@shoelace-style/shoelace/dist/components/alert/alert.js';

// Auto dark mode (optional). If user prefers dark, add class so overrides in theme.css can react.
if (window.matchMedia && window.matchMedia('(prefers-color-scheme: dark)').matches) {
	document.documentElement.classList.add('sl-theme-dark');
	// Dynamic import of dark theme only if needed
	import('@shoelace-style/shoelace/dist/themes/dark.css');
}
// Listen for changes
window.matchMedia('(prefers-color-scheme: dark)').addEventListener('change', e => {
	if (e.matches) {
		document.documentElement.classList.add('sl-theme-dark');
		import('@shoelace-style/shoelace/dist/themes/dark.css');
	} else {
		document.documentElement.classList.remove('sl-theme-dark');
	}
});

console.log('Shoelace components + themes loaded');
