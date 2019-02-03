const React = require('react');
const ReactDOM = require('react-dom');

class App extends React.Component {

	constructor(props) {
		super(props);
	}

	componentDidMount() {
	}

	render() {
		return (
				<b>Hello!</b>
		)
	}
}

ReactDOM.render(
	<App />,
	document.getElementById('react')
)
