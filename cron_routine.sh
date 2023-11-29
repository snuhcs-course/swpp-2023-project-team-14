# kill all existing tmux sessions
tmux kill-server || true

# create a new tmux session
tmux new-session -d -s ml_session

# make the script executable
chmod +x recommendation_pipeline.sh

# run the script inside the tmux session
tmux send-keys -t ml_session "./recommendation_pipeline.sh" C-m

# detach from the tmux session
tmux detach-client -s ml_session