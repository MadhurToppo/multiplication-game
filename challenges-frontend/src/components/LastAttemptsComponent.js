import * as React from 'react';

class LastAttemptsComponent extends React.Component {

    render() {
        return (
            <table>
                <thead>
                <tr>
                    <th>Challenge</th>
                    <th>Your guess</th>
                    <th>Correct</th>
                </tr>
                </thead>
                <tbody>
                {this.props.lastAttempts.map(a =>
                    <tr key={a.id}
                        style={{ color: a.correct ? 'green' : 'red' }}>
                        <td>{a.challenge.factorA} x {a.challenge.factorB}</td>
                        <td>{a.resultAttempt}</td>
                        <td>{a.correct ? "Correct" :
                            ("Incorrect (" + a.challenge.factorA * a.challenge.factorB + ")")}</td>
                    </tr>
                )}
                </tbody>
            </table>
        );
    }
}

export default LastAttemptsComponent;