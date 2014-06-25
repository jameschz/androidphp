/*
 * StringBuffer class
 */
function StringBuffer() {
	this.buffer = [];
}
StringBuffer.prototype.append = function append (string) {
	this.buffer.push(string);
	return this;
};
StringBuffer.prototype.toString = function toString () {
	return this.buffer.join("");
};
/*
 * Strip html tags
 */
function stripTags (str) {
	return str.replace(/<[^>].*?>/g,"");
}
/*
 * Display json with better look
 */
function formatJson (str) {
	var tab = 0;
	var buf = new StringBuffer();
	for (var i = 0; i < str.length; i++) {
		var char = str.charAt(i);
		if (char == '{' || char == '[') {
			tab++;
			char = char + "\n";
			for (var j = 0; j < tab; j++) char = char + "\t";
		}
		if (char == '}' || char == ']') {
			tab--;
			for (var j = 0; j < tab; j++) char = "\t" + char;
			char = "\n" + char;
		}
		if (char == ',') {
			char = char + "\n";
			for (var j = 0; j < tab; j++) char = char + "\t";
		}
		buf.append(char);
	}
	return buf.toString();
}