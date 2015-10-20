// JavaScript Document

function pseudoPseudoRandom(threshold) {
	var l = NaN;
	var i;
	do {
		i = Math.random();
	} while (Math.abs(l - i) < threshold);
	return l = i;
}