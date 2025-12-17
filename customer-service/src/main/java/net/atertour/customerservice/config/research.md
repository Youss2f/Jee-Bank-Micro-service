Perfect. Now I have comprehensive information from the major research areas. Let me compile this into a structured technical literature review addressing the user's request for SOTA methodologies, mathematical limitations, and white space opportunities.

## Literature Review: Causal Discovery Algorithms for Cybersecurity Event Logs

### State-of-the-Art (SOTA) Methodologies

#### 1. Granger Causality and Point Process Models

**Current Implementations:**

The most mature SOTA approach leverages **multivariate Hawkes processes** for causal discovery from event sequences. Instance-wise Self-Attentive Hawkes Processes (ISAHP) represents the cutting edge, meeting formal requirements of Granger causality through transformer-based attention mechanisms that identify event-instance-level causal structures in asynchronous multi-type event sequences.[1][2]

Key algorithmic specifics:
- Hawkes process intensity function: \(\lambda^*(t) = \mu + \sum_{t_j < t} g(t - t_j)\) where \(g(\cdot)\) is the temporal decay kernel
- Granger causality formally defined: variable X Granger-causes Y if past values of X improve prediction of Y beyond Y's own history
- Sparse regularization via \(\ell_0\) or \(\ell_1\) penalties to identify causal edges in high-dimensional settings

**Cybersecurity Applications:**
Attack log analysis employs Granger Causality Testing (GCT) with pairwise correlation analysis between alert sequences, using Granger Causality Index (GCI) values computed from residuals of Vector AutoRegression models. The topological Hawkes process (THP) extension incorporates network dependencies, modeling excitation/inhibition relationships among event types through graph convolution mechanisms.[3][4][5]

**Mathematical Constraints:**
- Strong assumption of stationarity in intensity function parameters
- Requires high-resolution event timing; discrete-time Hawkes processes suffer from **identifiability issues with instantaneous effects**—causal relationships occurring simultaneously at low sampling rates become indistinguishable[6][7]
- Multicollinearity in infectivity matrices under certain parameter regimes (productivity matrix eigenvalue approaching 1) induces spurious cross-excitation discovery[8]

#### 2. Constraint-Based Causal Discovery (Temporal Extensions)

**SOTA Algorithms:**

- **PCMCI (Peter-Clark Momentary Conditional Independence)**: Extends PC algorithm with momentary conditional independence tests for time series; proven consistent under non-linearity
- **tsFCI and SVAR-FCI**: Handle latent confounders by producing Partial Ancestral Graphs (PAGs) instead of DAGs; SVAR variant specialized for sparse VAR models
- **LPCMCI**: Local variant conditioning only on parent sets of target variables, reducing computational burden

**Cybersecurity-Specific Constraint-Based Methods:**
Additive Nonlinear Time Series Models (ANLTSM) framework leverages constraint-based inference assuming linear contemporaneous confounding effects while permitting nonlinear lagged dependencies. Applied to alert correlation with conditional independence tests on kernel regression residuals.[9]

**Critical Mathematical Limitations:**
1. **Causal Sufficiency Assumption**: All methods assuming causal sufficiency fail when latent confounders exist (ubiquitous in cybersecurity where unobserved attacker motivations influence multiple surface events)
2. **Faithfulness Violations**: Parameter cancellations in linear Gaussian models violate faithfulness assumption; e.g., coefficient vanishing \(a \cdot b + c = 0\) creates statistical independence between nodes that remain causally connected[10]
3. **Conditional Independence Testing in High Dimensions**: Curse of dimensionality affects nonparametric CI tests; conditioning set size exponentially impacts reliability
4. **Temporal Sampling Effects**: Summary causal graphs (collapsing to single nodes per variable) do not preserve interventional semantics of full-time causal graphs—prevent valid do-calculus counterfactual reasoning

#### 3. DAG Learning via Continuous Optimization

**SOTA Score-Based Methods:**

- **DYNOTEARS** (2020): Dynamic extension via differentiable continuous optimization with acyclic constraint \(\text{tr}((I - \Theta)^{-1}) = d\) where \(\Theta\) is weighted adjacency matrix
- **NTS-NOTEARS**: Handles nonlinear mechanisms through neural networks with continuous acyclicity relaxation
- **ExDAG** (Exact DAG Learning): Mixed-integer quadratic programming formulation for exact recovery on graphs up to 50 vertices

**Identifiability Results:**
- Linear models require **non-Gaussian noise** for identifiability; Gaussian noise admits Markov equivalent DAGs indistinguishable from data[11]
- Identifiability under latent confounding: Linear non-Gaussian models identifiable up to parameter scaling if causal sufficiency fails under pervasive confounding conditions; requires overcomplete ICA equivalence checking[12][11]
- **Graphical Criterion for Generic Identifiability**: For latent variable linear models, direct causal effects identifiable if no variable has both unconfounded parents and unmeasured confounders[13]

**Attack Graph Construction Specifics:**
Recent attack graph synthesis leverages DAG learning on system provenance data (process execution logs) where nodes represent system entities (files, processes, network sockets) and edges denote dependencies/causality relationships. Continuous optimization methods scale to ~1000s nodes with learned constraints based on domain ontology (e.g., process cannot write to file before file creation event).

#### 4. Graph Neural Network Approaches for Temporal Threat Detection

**SOTA Architectures:**

- **Spatio-Temporal GNN Autoencoders**: Combination of Graph Attention Networks (GAT) for spatial structure + Gated Recurrent Units (GRU) for temporal evolution; achieves APT detection with lower false positive rates[14][9]
- **Temporal Graph Attention (TGAT)**: Applies positional attention encodings replaced by temporal encodings using Random Fourier Features (RFF); operates on temporal neighborhoods NT[v]
- **Temporal Neighborhood Mechanisms**: "Mailbox" approach maintaining per-node message history from recent events, aggregated via learned update functions

**Cybersecurity-Specific Implementations:**
Flash IDS employs semantic word2vec encoders capturing process/file names + temporal ordering within provenance graphs, with GCN-based encoders for both local and global graph structures. Embedding recycling database strategy addresses computational overhead for large provenance graphs (~millions of nodes in production systems).

**Mathematical Limitations of GNNs for Temporal Security:**

1. **Over-Smoothing Problem**: With L layers, learned node embeddings converge to constant after ~5-7 layers, losing discriminative power in distinguishing benign vs. malicious activity patterns. Explicit solution lacking for temporal graphs[15]

2. **Over-Squashing**: Information from distant temporal neighbors fails to propagate to current node due to exponential growth of neighborhood sizes; adversary can exploit by spreading attack causality across temporal hops beyond network's receptive field[16]

3. **Temporal Embedding Limitations**: Random Fourier Features encode only elapsed time \(\Delta t = t - t'\); cannot capture periodic patterns (e.g., scheduled maintenance events triggering alerts at specific hours) without explicit periodicity priors

4. **Scalability Constraints**: Event-based models with mailbox mechanisms face memory explosion in dense alert streams; no principled approach to mailbox size selection or staleness management[15]

5. **Staleness Problem**: In continuous-time models, node memory updated infrequently becomes outdated; long-range temporal dependencies unobserved, creating confounding where old attack patterns incorrectly influence current threat assessment

***

### Specific Mathematical Limitations of Current Approaches

#### 1. Identifiability with Unobserved Confounding

**Formal Challenge:**
For cybersecurity alerts \(\mathbf{x} = (x_1^{\text{network}}, x_2^{\text{host}}, x_3^{\text{app}})\), if unobserved attacker intent \(\mathbf{u}\) influences multiple alert types:

\[
\begin{align}
x_1^{\text{network}} &:= f_1(\mathbf{u}, v_1)\\
x_2^{\text{host}} &:= f_2(x_1^{\text{network}}, \mathbf{u}, v_2)\\
x_3^{\text{app}} &:= f_3(x_2^{\text{host}}, v_3)
\end{align}
\]

Traditional causal discovery identifies \(x_1 \to x_2\) correctly but spurious edge \(x_1 \to x_3\) appears (mediation indistinguishable from confounding without additional constraints). Even with latent variable methods, identifiability requires:
- **Rank Condition**: Covariance matrix restricted to observed variables must have specific rank properties revealing latent structure[17]
- **Pervasive Confounding Structure**: Number of unobserved confounders must satisfy \(m < d\) (fewer hidden confounders than observed variables) plus genericity assumptions on causal coefficients[18]

**Cybersecurity Implication:**
APT attack chains involve dozens of unobserved intermediate steps (e.g., attacker privilege escalation before privilege escalation alert generation); unobserved confounding assumption violated, rendering standard identifiability guarantees invalid.

#### 2. Temporal Subsampling and Aliasing Effects

**Mathematical Formulation:**
If true causal process operates at sampling rate \(f_s\) but events logged at rate \(f_{\text{log}} \ll f_s\), then unobserved intermediary events create **temporal aliasing**:

True model: \(\mathbf{x}(t) = A\mathbf{x}(t - \delta t) + \mathbf{u}(t)\) where \(\delta t = 1/f_s\)

Observed (subsampled): \(\mathbf{x}(t_k) = A^K \mathbf{x}(t_{k-1}) + \text{hidden states}\)

This induces systematic bias in causal strength estimation; inferred lag-1 effect \(\tilde{A} \approx A^K\) conflates multi-step causal chains into single edges.

**Solution Constraints:**
Proxy-based causal discovery exploits temporal structure—future observations serve as proxies for unobserved intermediate states—but requires **strong assumptions** on proxy informativeness (future events causally depend only on unobserved hidden variables, not on observed confounders).[19]

#### 3. Faithfulness Assumption Failures in Cybersecurity

Real attack traces frequently violate faithfulness through:

- **Parameter Cancellation**: Alert generation logic implementing \(A_{\text{trigger}} = f_1 + f_2\) where \(f_1\) and \(f_2\) represent independent attack signatures; if implemented as \(A_{\text{trigger}} := 0.5 f_1(\text{config1}) - 0.5 f_2(\text{config1})\), cancellation creates statistical independence between config1 and A_trigger despite causal dependence

- **Conditional Independence from Code Structure**: SIEM rule design (e.g., "Alert if (host_cpu > 80%) AND (NOT scheduled_maintenance)") creates conditional independence \(\text{host\_cpu} \perp \perp \text{process\_restart} | \text{scheduled\_maintenance}\) not implied by true causal graph

#### 4. DAG Learning Constraints Under Feedback Loops

**Mathematical Challenge:**
Cyclic attack patterns (e.g., worm re-infection cycles creating oscillating alert patterns) violate acyclicity assumption fundamental to DAG-based methods. Extensions handling cycles (MissNODAG, cyclic causal models) require:

- **Acyclicity Relaxation**: Parametric models allowing feedback with stability constraints \(\|\lambda_{\max}(A)\| < 1\) where A is transition matrix
- **Identifiability Cost**: Cyclic models require additional assumptions beyond acyclicity (e.g., no instantaneous cycles, specific functional forms)

***

### Identified White Space for Novel Contribution

#### 1. **Multi-Order Dynamic Causality in Temporal Point Processes**

**Gap:**
Current Hawkes process methods assume pairwise causal relationships; attack chains typically involve 3+-order dependencies (event A causally influences event B, which influences C, where this 2-hop path has stronger predictive signal than direct A→C). MOCHA framework discovers multi-hop causal paths over time-evolving DAGs but not specialized for cybersecurity alert semantics.[20]

**Novel Contribution Opportunity:**
- Develop **typed temporal point processes** respecting attack ontology (reconnaissance → weaponization → delivery → exploitation)
- Formalize **temporal distance metrics** for causality: causal effect strength decays with temporal separation following attack-specific kernels (e.g., exponential for lateral movement, step-function for post-exploitation persistence)
- **Algorithmic Innovation**: Differentiable DAG learning with hierarchical constraints encoding attack prerequisites (exploitation requires prior weaponization discovery)

#### 2. **Handling Heterogeneous Temporal Dependencies in Alert Streams**

**Gap:**
GNNs treat all temporal edges uniformly; cybersecurity logs exhibit **mode-switching dynamics**:
- Reconnaissance phase: many low-confidence alerts with correlated timestamps
- Exploitation phase: sparse high-confidence alerts with specific timing constraints
- Persistence phase: periodic backdoor activation alerts

Current temporal GNNs lack mixture models capturing mode-specific temporal patterns.

**Novel Contribution Opportunity:**
- **Latent Regime-Switching Temporal GNNs**: Model alert stream as mixture of \(K\) distinct causal regimes, each with learned regime-specific temporal decay kernels
- **Mathematical Framework**: Extend Hidden Markov Models to graph domain; regime probabilities updated via graph attention mechanisms on current alert neighborhoods
- **White Space**: Principled regime inference without labeled attack phase annotations (unsupervised discovery of attack lifecycle stages from alert timing patterns alone)

#### 3. **Causal Discovery with Expert-in-the-Loop Constraints**

**Gap:**
Security analysts provide domain knowledge (e.g., "process_created events cannot cause file_access events at same timestamp") underutilized in current methods. Hard constraints reduce search space exponentially; soft constraints via Bayesian priors insufficient for fine-grained constraints.

**Novel Contribution Opportunity:**
- **Differentiable Constraint-Based DAG Learning** (DAGPA framework nascent; not specialized for cybersecurity)[21]
- Formalize expert knowledge as soft/hard logic constraints in continuous optimization:

\[
\min_{\Theta} L(\Theta) \quad \text{s.t.} \quad \text{acyclic}(\Theta), \quad h_{\text{expert}}(\Theta) \leq \epsilon
\]

where \(h_{\text{expert}}\) encodes domain rules (e.g., distance between alert types ≥ k implies not directly causal)
- **Scalability**: Current solutions in integer programming; develop warm-start procedures leveraging expert constraints to initialize continuous optimization

#### 4. **Temporal Confounding from Alert Aggregation/Deduplication**

**Gap:**
Existing methods assume each log entry represents atomic event. Real SIEMs aggregate alerts: \(n\) raw events mapped to 1 deduplicated alert with aggregated timestamp. This aggregation introduces **temporal confounding**—multiple causally unrelated events appear simultaneous, producing spurious correlations.

**Novel Contribution Opportunity:**
- **Deconvolution Approach**: Learn latent high-resolution event stream from deduplicated alerts; model aggregation process as mixture of Poisson processes with unknown rates
- **Identifiability Conditions**: Establish sufficient statistics for recovering causal graph from aggregated observations
- **Causal Mechanism Shifts** (emerging technique): Exploit domain shifts (different alert aggregation windows across environments/time periods) as implicit interventions revealing causal structure[22]

#### 5. **Separating Attack Causality from Correlation at High Alert Velocity**

**Gap:**
In high-frequency alert streams (>1000 alerts/sec in enterprise networks), many alerts correlate temporally without causal relationship (e.g., multiple IDS signatures triggered by single attack session). High false positive rates in current GNN-based detection methods (alert fatigue).

**Novel Contribution Opportunity:**
- **Functional Temporal Causal Models (FCM)** extension: Model alert \(x_i(t)\) as functional data, discovering causality between smooth functions rather than discrete events
- **Non-Gaussian Additive Noise Models** in continuous-time domain:

\[
x_i(t) = f_i(t, \mathbf{x}_{\text{pa}(i)}(t - \tau_i)) + u_i(t)
\]

where delay \(\tau_i\) learned per causal edge
- **Sparsification via MDL Principle**: Minimum Description Length scores for identifying true causal edges from noise-induced correlations

#### 6. **Multi-Layer Attack Graph Causality**

**Gap:**
Provenance graphs encode system-level causality (process ancestry, file dependencies). Network-level causality (IP packet causality) and application-level causality (API call chains) remain siloed. Multi-layer causal discovery maintaining semantic consistency across abstraction levels unexplored.

**Novel Contribution Opportunity:**
- **Heterogeneous Temporal Graph Causal Discovery**: Extend typed point processes to multi-typed nodes (system, network, application primitives)
- **Cross-Layer Causality Constraints**: Formalize constraints ensuring system-level and network-level causal graphs consistent (e.g., process causality implies network packet causality only through specific interface types)
- **Algorithmic Framework**: Simultaneous structure learning across layers with alignment objectives (learned cross-layer edges minimizing description length given layer-specific structures)

***

### Summary Table: SOTA vs. Limitations vs. White Space

| **SOTA Method** | **Key Mathematical Innovation** | **Critical Limitation** | **White Space Opportunity** |
|---|---|---|---|
| Hawkes/ISAHP | Granger causality at event instance level | Instantaneous causality ambiguity in discrete time; multicollinearity under critical regimes | Multi-order dynamic causality; attack ontology-aware kernels |
| PCMCI/tsFCI | Constraint-based with latent confounders | Causal sufficiency relaxed but requires linear confounding; CI test reliability in high-dims | Expert-in-the-loop soft/hard constraints in continuous optimization |
| DYNOTEARS/NOTEARS | Continuous DAG optimization with neural mechanisms | Nonlinear mechanisms unidentifiable without non-Gaussian noise; feedback loops unsupported | Cyclic causality with regime-switching; temporal aggregation deconvolution |
| Spatio-Temporal GNNs | Graph attention + RNN temporal modeling | Over-smoothing/squashing in deep layers; uniform temporal embedding; scalability bottlenecks | Latent regime-switching TGNNs; multi-layer heterogeneous graph causality |
| tsFCI + Proxy Methods | Subsampling handling via future proxy variables | Strong informativeness assumptions; identifiability conditions hard to verify | Functional temporal causal models; cross-alert-aggregation causality |
---
---
---
The following analysis defines the current mathematical boundaries of automated defense as of December 2025.

### **Executive Summary: The Shift to Equilibrium-Based Defense**

In late 2024 and throughout 2025, the field of adversarial machine learning for cyber defense shifted from heuristic reinforcement learning (RL) to **equilibrium-centric frameworks**. We have moved beyond training agents to merely "beat" a static opponent toward training agents that converge to **Nash Equilibria (NE)** or **Generalized Nash Equilibria (GNE)**.

The current mathematical frontier lies in **Policy Space Response Oracles (PSRO)** and **Robust Markov Games**. Theoretically, we have established convergence proofs for safe MARL in constrained environments (GNE). Practically, however, computing exact equilibria in high-dimensional, dynamic networks remains intractable. Consequently, the "state of the art" in implementation has bifurcated: theoretical papers prove properties of *Robust Nash* in simplified settings, while deployed systems utilize *Empirical Game-Theoretic Analysis (EGTA)* and *Evolutionary Dynamics* (e.g., population-based training) to find approximate solutions in complex simulators like CybORG.

***

### **1. Mathematical Boundaries: Convergence & Equilibrium Concepts**

The core challenge in autonomous defense is the **non-stationarity** of the opponent: as the Blue Team (defender) learns, the Red Team (attacker) adapts. Standard RL fails here. The mathematical solution has formalized around three key equilibrium concepts:

#### **A. Generalized Nash Equilibrium (GNE) for Safe Defense**
Standard Nash Equilibrium assumes unconstrained strategy spaces. Cyber defense, however, imposes hard safety constraints (e.g., "do not isolate the critical server").
*   **The Boundary:** Recent work (Nov 2024) has extended MARL to converge to **GNE** on *safety value functions*. This involves identifying the **Controlled Invariant Set (CIS)**—the region of state space where a defender can guarantee safety regardless of the attacker's actions.[1]
*   **Significance:** This proves that agents can learn policies that are not just optimal in the limit, but *provably safe* during the convergence process, addressing the "safe exploration" problem.

#### **B. Robust Nash and Coarse Correlated Equilibria (CCE)**
In infinite multiplayer games, finding a pure Nash Equilibrium is often PPAD-hard or impossible.
*   **The Boundary:** To break the "curse of multiagency," researchers have relaxed the requirement from perfect Nash to **Coarse Correlated Equilibrium (CCE)** and **Robust Nash Equilibrium**.[2]
*   **Robustness:** New formulations (Jan 2025) model the game as a **Robust Markov Game (RMG)**. Here, the defender optimizes against a *distributional uncertainty set* of attacker policies (an ambiguity set) rather than a single worst-case adversary. This bridges the "sim-to-real" gap by creating policies resilient to entire classes of attacks rather than specific exploits.

#### **C. Nash Q-Learning & De-coupled Architectures**
Standard Q-learning diverges in multi-agent settings.
*   **The Boundary:** The **Nash Q-Network (2025)** explicitly decouples the *critic* (who estimates joint state-action values to construct a payoff matrix) from the *actor* (who updates policies toward the Nash distribution of that matrix).[3][4]
*   **Result:** This provides a constructive algorithm for finding NE in high-dimensional, partially observable stochastic games (POSGs), previously considered solvable only in tabular settings.

***

### **2. Evolutionary Game Theory: Red vs. Blue Dynamics**

When the strategy space is too large to compute a full payoff matrix (e.g., the infinite space of all possible malware variants), we rely on **Evolutionary Game Theory**.

#### **A. Policy Space Response Oracles (PSRO) & Double Oracle**
The dominant framework in 2025 is **PSRO** (a "Double Oracle" method).
*   **Mechanism:** Instead of solving the full game, we iteratively grow a "population" of policies. In each epoch, an "Oracle" (RL agent) trains to find a Best Response to the current meta-strategy of the opponent population.
*   **Mathematical Implication:** This reduces an infinite game to a finite **Empirical Game (Meta-Game)**. The "solution" is a mixed strategy over this finite population (e.g., "Play Policy A 30% of the time, Policy B 70%").[5][6]
*   **Recent Advance:** **Flip-PSRO (Aug 2025)** demonstrates this in resource-contention games (FlipIt), showing that population-based defenders generalize 2x better against unseen attacks than standard RL agents.[5]

#### **B. Evolutionary Population Dynamics**
Theoretical models now map RL training steps to **Replicator Dynamics** equations.
*   **Population-Based Training (PBT):** Rather than a single agent, we maintain a diverse *league* of agents. Evolutionary operators (crossover, mutation) are applied to policy weights to escape local optima in the non-convex strategy landscape.[7]
*   **Meta-Game Analysis:** By analyzing the *empirical payoff matrix* generated by these populations, we can detect "cycles" (Rock-Paper-Scissors dynamics) where no single policy dominates, forcing the defender to adopt a randomized mixed strategy to remain unexploitable.[8]

***

### **3. Dynamic Network Games & Graph Neural Networks**

Cyber networks are not static grids; nodes (devices) are added, removed, or reconfigured.

*   **The Problem:** Standard MARL inputs fixed-size vectors, failing when network topology changes (dynamic graphs).
*   **The Solution:** **Graph Neural Networks (GNN) + MARL**.
    *   **Generalizable Graph-RL (Sep 2025):** Agents process the network state as a graph embedding. This makes the policy **topology-invariant**—a defender trained on a 10-node network can deploy effectively on a 100-node network without retraining.[9][10]
    *   **Capsule GNNs:** Used to identify attack locations in power grids even as the grid topology shifts dynamically.[11]

***

### **4. Distinction: Theoretical Papers vs. Actual Implementations**

The following table distinguishes between the mathematical ideal (Theory) and the engineering reality (Implementation) as of late 2025.

| Feature | **Theoretical Papers (ArXiv/Math)** | **Actual Implementations (Simulators/Gyms)** |
| :--- | :--- | :--- |
| **Game Type** | Infinite-Horizon Partially Observable Stochastic Games (POSGs). | Finite-Horizon Games (e.g., 50-step episodes in *CybORG*, *NetworkGym*). |
| **Equilibrium** | **Exact** Nash or Generalized Nash Equilibrium (GNE) with formal convergence proofs[12][1]. | **Approximate** Equilibrium via *Empirical Game-Theoretic Analysis* (EGTA) or PSRO[8][6]. |
| **Adversary** | **Worst-Case Optimal:** Assumes the attacker plays a perfect best response (minimax). | **Heuristic / Adaptive:** Attacker is often a scripted agent (e.g., "B_line" in CybORG) or a PPO agent with bounded compute[13]. |
| **State Space** | Continuous or abstract discrete states ($S \in \mathbb{R}^n$). | High-dimensional discrete vectors (system logs, subnet status, observable alerts)[3]. |
| **Safety** | **Guaranteed:** Proven via Controlled Invariant Sets (CIS) and barrier functions[1]. | **Soft-Constrained:** Penalties (negative rewards) for unsafe actions; safety is probabilistic, not guaranteed[12]. |
| **Key Algorithm** | *Dual Policy Iteration*, *Robust Bellman Updates*. | *PPO*, *Masked-PPO*, *DQN*, *Nash Q-Network*, *Flip-PSRO*. |

### **Summary of Key Citations**
*   ** (Sep 2025):** Formal convergence proofs for MARL in Microgrids using AHP payoffs.[12]
*   ** (Aug 2025):** Nash Q-Network implementation in complex cyber defense simulation.[3]
*   ** (Nov 2024):** Theory of Safe MARL converging to Generalized Nash Equilibrium.[1]
*   ** (Aug 2025):** "Flip-PSRO" and population-based training for robust generalization.[5]
*   ** (Jun 2025):** Review of Evolutionary Game Theory merging with MARL.[7]
*   ** (Jan 2025):** Empirical Game-Theoretic Analysis (EGTA) of autonomous agents.[6]

---
---
---
Excellent. I have comprehensive research materials. Now let me compile the final report.

## Mathematical Foundations of Alert Prioritization in SOCs: Information Theory and Signal Processing Optimization

### Executive Summary

Alert prioritization in Security Operations Centers (SOCs) represents a critical application of advanced information-theoretic principles and signal processing techniques. The mathematical challenges center on optimizing the **Signal-to-Noise Ratio (SNR)** in high-volume event streams while simultaneously minimizing false positives and false negatives. This analysis reveals that optimal SOC alert systems emerge from: (1) **Neyman-Pearson hypothesis testing frameworks**, (2) **Kullback-Leibler divergence-based likelihood ratio optimization**, (3) **Bayesian inference with adaptive posterior belief updating**, (4) **sparse coding and compressive sensing for dimensionality reduction**, and (5) **game-theoretic models accounting for strategic evasion**.[1][2][3][4][5][6][7]

***

### 1. Information-Theoretic Foundations: Shannon Entropy and Channel Capacity

#### 1.1 Shannon Entropy as an Anomaly Detection Metric

Shannon entropy provides the mathematical foundation for quantifying randomness and information content in network traffic. For a discrete random variable $X$ representing traffic features, Shannon entropy is defined as:[8][9]

\[
H(X) = -\sum_{i=1}^{n} p(x_i) \log_2 p(x_i)
\]

In the SOC context, entropy measures the **disorder** or **predictability** of observed network traffic. Normal traffic exhibits characteristic entropy patterns, while anomalous traffic (particularly DDoS attacks and intrusions) manifests as entropy deviations from the baseline.[9][8]

**Application to SOC Alert Prioritization:** 
Entropy-based detection establishes a **statistical hypothesis test** where:
- $H_0$: Observed traffic is normal (entropy ≈ baseline entropy $H_{\text{normal}}$)
- $H_1$: Observed traffic is anomalous (entropy >> $H_{\text{normal}}$ or << $H_{\text{normal}}$)

The **entropy deviation** acts as a natural SNR metric: signals with high information deviation (entropy change) relative to noise (baseline entropy variance) warrant prioritization.[8]

#### 1.2 Mutual Information for Feature Relevance

Mutual information between features and attack labels provides a principled method for dimensionality reduction and feature selection in SOCs:[10][11][12]

\[
I(X;Y) = \sum_{x} \sum_{y} p(x,y) \log \frac{p(x,y)}{p(x)p(y)}
\]

This measures the **information gain** from observing feature $X$ about the class label $Y$ (benign vs. malicious). Features with high mutual information contribute most to attack detection accuracy, allowing SOCs to focus analysis on the most informative signals.[10]

**Optimization Principle:** SOC systems should prioritize alerts associated with features exhibiting \(\max(I(X_i; Y))\) over feature subsets, effectively filtering noise (low mutual information features) and amplifying signal (high mutual information features).[13][14]

***

### 2. Neyman-Pearson Hypothesis Testing and Optimal Detection

#### 2.1 The Fundamental NP Lemma

The **Neyman-Pearson Lemma**  establishes the mathematically optimal decision rule for binary hypothesis testing. In SOC alert prioritization, the detector (alert system) faces:[15][16]

\[
H_0: \text{Event stream is normal} \quad (f_0(x))
\]
\[
H_1: \text{Event stream contains intrusion} \quad (f_1(x))
\]

The optimal detector solves:[6][7]

\[
\max_{\delta \in \Gamma} \int_{\delta(m)=1} f_1(m) dm \quad \text{s.t.} \quad \int_{\delta(m)=1} f_0(m) dm \leq \alpha
\]

where $\alpha$ is the **false alarm rate constraint**. The solution is the **likelihood ratio test:**[7][16][6]

\[
\delta^*(m) = \begin{cases} 1 & \text{if } \frac{f_1(m)}{f_0(m)} > \lambda^* \\ 0 & \text{otherwise} \end{cases}
\]

The **threshold** $\lambda^*$ is determined by the false alarm constraint $\alpha$.

**SNR Optimization Interpretation:**
- The **signal** is the likelihood ratio $\frac{f_1(m)}{f_0(m)}$ when $H_1$ is true
- The **noise** comprises random fluctuations in the likelihood ratio under $H_0$
- The **SNR** is maximized when the threshold $\lambda^*$ is optimally set based on the detection-false alarm tradeoff[16][15]

This principle directly translates to SOC alert systems: prioritize alerts with the **highest likelihood ratio** (highest confidence of being true positives).

#### 2.2 Receiver Operating Characteristic (ROC) Curves

The NP framework generates **ROC curves** characterizing the fundamental tradeoff between detection rate (true positive rate) and false alarm rate:[17][15][6]

\[
\text{TPR} = P(\delta(m) = 1 | H_1 \text{ true})
\]
\[
\text{FPR} = P(\delta(m) = 1 | H_0 \text{ true})
\]

The ROC curve traces all achievable (FPR, TPR) pairs as the threshold $\lambda$ varies. **Alert prioritization should focus on the upper-left corner** of ROC space (high TPR, low FPR).[6]

***

### 3. Kullback-Leibler Divergence and Information-Theoretic Optimality

#### 3.1 KL Divergence as a Penalty for Detection Degradation

The **Kullback-Leibler (KL) divergence** measures the statistical distance between two probability distributions $p$ and $q$:[5][18][6]

\[
D_{\text{KL}}(p \| q) = \sum_{x} p(x) \log \frac{p(x)}{q(x)}
\]

In adversarial cybersecurity contexts, an attacker **distorts** the measured distribution $q$ (observed traffic) away from the normal distribution $p$ (baseline traffic) to evade detection. The **KL divergence quantifies this distortion.**[5][6]

**Formulation in Evasion-Aware Detection:**[6]

An attacker minimizes the detector's performance while incurring a **distortion cost:**

\[
\min_{\sigma_1, \sigma_0} \int_{\delta^*(m)=1} \sigma_1(m) dm + \lambda \sum_{i=0}^1 \int_M \sigma_i(m) \ln \frac{\sigma_i(m)}{f_i(m)} dm
\]

The first term is the **detection rate** the attacker wishes to minimize. The second term is the **KL divergence penalty** (with weight $\lambda$) preventing the attacker from distorting distributions too drastically (which would increase the SNR and trigger alternative detection mechanisms).[6]

**Interpretation for SOC SNR Optimization:**
- High $\lambda$ (strict KL constraint): Attacker cannot significantly distort traffic distributions, and the detector can rely on clean statistical tests
- Low $\lambda$ (loose KL constraint): Attacker can distort substantially, reducing detection SNR
- **Optimal alert prioritization must account for potential KL divergence-based evasion strategies**[5][6]

***

### 4. Bayesian Inference and Adaptive Posterior Belief Updates

#### 4.1 Bayesian Alert Scoring

Bayesian networks  formalize the SOC analyst's intuitive reasoning: **combine multiple noisy signals into a coherent risk assessment.**[3][19][20]

Let $A$ denote the event "attack is occurring" and $e_1, e_2, \ldots, e_n$ be observed evidence (individual alerts). By Bayes' rule:[3]

\[
P(A | e_1, \ldots, e_n) = \frac{P(e_1, \ldots, e_n | A) P(A)}{P(e_1, \ldots, e_n)}
\]

**Multi-Stage Alert Prioritization:**
1. Start with a **low prior** $P(A)$ (assumes events are normal)
2. As evidence arrives, **update the posterior** $P(A | e_i)$
3. **Prioritize alerts** corresponding to features with high likelihood ratio** $\frac{P(e_i | A)}{P(e_i | \neg A)}$

This avoids **double-counting** issues that plague naive alert fusion systems.[3]

#### 4.2 Information Gain for Alert Selection

The **information gain** from observing a new alert $e$ before deciding on the hypothesis is:[3]

\[
\text{InfoGain}(e) = H(A) - H(A | e) = D_{\text{KL}}(P(A|e) \| P(A))
\]

where $H(A)$ is Shannon entropy. **Alerts with the highest information gain** (greatest reduction in uncertainty about the attack hypothesis) should be prioritized.[3]

***

### 5. Sparse Coding and Compressive Sensing for High-Volume Event Streams

#### 5.1 Compressive Sensing for Dimensionality Reduction

Modern SOCs observe $N$ traffic features across high-dimensional spaces. **Compressive Sensing (CS)**  enables **optimal sampling** below the Nyquist rate while preserving the signal of interest (attacks).[21][22][23]

The CS principle: if a signal $x \in \mathbb{R}^N$ is **$k$-sparse** (only $k$ non-zero components in some basis), it can be recovered from $m = O(k \log N)$ random linear measurements:[21]

\[
Y = \Phi x, \quad \Phi \in \mathbb{R}^{m \times N}, \quad m \ll N
\]

**Application to SOC Alert Prioritization:**
- Most of the $N$ features in network traffic are **irrelevant** to attack detection (sparse)
- CS automatically identifies the **sparse subset** of attack-relevant features
- Detection algorithms operate on compressed data, reducing false positives and improving SNR[22][21]

#### 5.2 SNR Optimization via Sparse Coding

Sparse representation of network traffic ensures that attack signatures manifest as **high-energy sparse vectors** distinct from normal traffic noise.[24][25][26][27][22]

For an intrusion detection model using **stacked sparse autoencoders (SSAE):**[24]

\[
\min_{W,b} \frac{1}{m} \sum_{i=1}^{m} L(x_i, \hat{x}_i(W,b)) + \lambda \sum_{l} \|W_l\|^2 + \rho \sum_j \text{KL}(p_0 \| \hat{p}_j)
\]

where:
- $L(\cdot)$ is the reconstruction error (signal fidelity)
- $\lambda$ is the weight decay (regularization)
- $\rho$ is the sparsity penalty coefficient
- $\text{KL}(p_0 \| \hat{p}_j)$ enforces that hidden units have **average activation** close to $p_0$ (typically $p_0 = 0.05$)

The **sparsity constraint** suppresses noise (low-activation neurons) while amplifying attack signals (high-activation neurons), thereby **optimizing the effective SNR**.[24]

***

### 6. Optimal Stopping Rules and Decision Thresholds

#### 6.1 Sequential Hypothesis Testing

In real-time SOCs, analysts face the question: **"When do I have enough evidence to declare an intrusion?"**

The **Sequential Probability Ratio Test (SPRT)**  addresses this through optimal stopping rules. Given samples $x_1, x_2, \ldots$ from either $H_0$ or $H_1$, compute the **cumulative likelihood ratio:**[28][29]

\[
\Lambda_n = \prod_{i=1}^{n} \frac{f_1(x_i)}{f_0(x_i)}
\]

The optimal stopping rule is:[28]

\[
\tau^* = \inf \left\{ n : \Lambda_n \geq \frac{1-\beta}{\alpha} \text{ or } \Lambda_n \leq \frac{\beta}{1-\alpha} \right\}
\]

where $\alpha$ is the false alarm rate and $\beta$ is the miss rate.

**SOC Application:** Instead of waiting for a fixed number of alerts or a static scoring threshold, use the **SPRT stopping rule** to declare an alert when the cumulative log-likelihood ratio crosses a dynamic threshold. This minimizes the **expected number of alerts analyzed** while maintaining error guarantees.[28]

#### 6.2 Information-Theoretic Stopping Criteria

For **mutual information-based feature selection** (analogous to alert prioritization), optimal stopping criteria arise from monitoring the **conditional mutual information** as features are sequentially added:[14][11][13]

\[
I(f_{\text{new}} ; Y | f_1, \ldots, f_k) = H(Y | f_1, \ldots, f_k) - H(Y | f_1, \ldots, f_k, f_{\text{new}})
\]

**Stop adding alerts to the prioritization set** when this conditional mutual information drops below a significance threshold (based on chi-square distribution with appropriate degrees of freedom).[13][14]

***

### 7. Game-Theoretic Optimal Detection Under Evasion

#### 7.1 Evasion-Aware Neyman-Pearson Detectors

Traditional NP detectors assume distributions $f_0, f_1$ are fixed. However, **adversarial attackers strategically distort their malicious traffic** to evade detection.[7][6]

The **game-theoretic NP detection framework** models this as a signaling game:[7][6]

**Proactive Detector's Decision Rule:**

Given distorted message $m$ received from potential attacker, update posterior belief via Bayes:

\[
p(H_1 | m) = \frac{\sigma_1(m) p(H_1)}{\sigma_1(m) p(H_1) + \sigma_0(m) p(H_0)}
\]

The detector decides:[6]

\[
\delta^*(m) = \begin{cases} 1 & \text{if } p(H_0 | m) < \alpha \\ 0 & \text{otherwise} \end{cases}
\]

Equivalently (using likelihood ratio):[6]

\[
\delta^*(m) = 1 \iff \frac{\sigma_1(m)}{\sigma_0(m)} > \beta, \quad \beta = \frac{p(H_0)}{p(H_1)} \left( \frac{1}{\alpha} - 1 \right)
\]

#### 7.2 Equilibrium Attacker Strategies

At the equilibrium of the signaling game, the attacker's strategy depends on the distortion penalty parameter $\lambda$:[6]

**Case 1: $\lambda = 0$ (No distortion cost)**
The attacker fully distorts malicious traffic to undetectable levels, concentrating it in regions where the detector will not look (regions where $\delta^* = 0$). Detection rate collapses to zero.[6]

**Case 2: $\lambda \to \infty$ (Infinite distortion cost)**
The attacker cannot modify traffic, and classical NP testing applies. Detection rate returns to the non-adversarial baseline.[6]

**Case 3: $0 < \lambda < \infty$ (Trade-off)**
The attacker's equilibrium strategies involve **interpolating** between the normal and abnormal distributions:[6]

\[
\sigma_1^*(m) = \begin{cases} c_1 f_1(m) & m \in M_0 \\ c_1 f_1(m) \left(\frac{f_1(m)}{f_0(m)}\right)^{-\frac{1}{1+\beta}} \zeta^{-\frac{1}{1+\beta}} & m \in M^* \\ c_1 f_1(m) e^{-1/\lambda} & m \in M_1 \end{cases}
\]

The regions $M_0, M^*, M_1$ are defined by thresholds on the likelihood ratio $\frac{f_1(m)}{f_0(m)}$.[6]

**SOC Alert Prioritization Implication:**
Systems must detect not just **absolute anomalies** but **distributions that have been adversarially manipulated.** The equilibrium analysis reveals where attackers would optimally concentrate distorted traffic, guiding defensive resource allocation.[7][6]

***

### 8. Practical Mathematical Model: Composite SNR in Event Streams

#### 8.1 Multi-Feature SNR Aggregation

In a real SOC processing $m$ simultaneous alert types, the **effective SNR** combines individual SNRs via the **Chi-square distribution** (under independence assumptions):[30][31]

For alert features $x_1, \ldots, x_m$ with individual SNRs $\text{SNR}_i = \frac{\mu_{1,i}^2}{\sigma_{0,i}^2}$, the **composite SNR** is approximately:[30]

\[
\text{SNR}_{\text{composite}} \approx \sum_{i=1}^{m} \text{SNR}_i \quad \text{(under weak signal regime)}
\]

However, under **strong signal** regimes (anomalies clearly present), the aggregation follows more complex Rayleigh statistics.[30]

**Alert Prioritization Rule:** Prioritize alerts whose **composite SNR exceeds a threshold** determined by the false alarm rate constraint $\alpha$.

#### 8.2 Dynamic Threshold Setting via Kullback-Leibler Rate

The **rate function** (from large deviations theory) characterizes how quickly detection probability approaches certainty:[32]

\[
I = D_{\text{KL}}(P_1 \| P_0)
\]

The **expected number of samples** needed to distinguish $P_1$ from $P_0$ with error rate $\epsilon$ scales as:[32]

\[
n^* \sim \frac{1}{I} \log \frac{1}{\epsilon}
\]

**SOC Implication:** Alerts with **higher KL divergence** (more information-theoretically distinct from normal behavior) require **fewer supporting observations** before triggering escalation. Set the alert threshold inversely proportional to $D_{\text{KL}}(f_{\text{attack}} \| f_{\text{normal}})$.[32]

***

### 9. Case Study: Shannon Entropy-Based DDoS Detection

#### 9.1 Mathematical Formulation

For DDoS attacks, the **source IP distribution** under attack deviates dramatically from normal:[9][8]

Normal traffic entropy:

\[
H_{\text{normal}} = -\sum_{i=1}^{n} p_i^{\text{normal}} \log_2 p_i^{\text{normal}}
\]

where $p_i^{\text{normal}}$ is the probability of observing source IP $i$ under normal conditions.

During DDoS, the distribution becomes **hyper-concentrated** on a few source IPs (botnet), reducing entropy:[8][9]

\[
H_{\text{DDoS}} \ll H_{\text{normal}}
\]

The **entropy deviation** serves as the SNR metric:

\[
\text{SNR}_{\text{entropy}} = \frac{(H_{\text{normal}} - H_{\text{DDoS}})^2}{\text{Var}(H | H_0)} \quad (\text{under normal distribution variance})
\]

#### 9.2 Empirical Results

Research demonstrates that Shannon entropy-based detection achieves **95% hit rate for abrupt DDoS changes** using only **25% of the uncompressed sample size**, validating the SNR optimization via compression.[33][9]

***

### 10. Fundamental Limits and Information-Theoretic Bounds

#### 10.1 The Fano Lemma

For distinguishing between $M$ hypotheses based on $n$ samples, the **Fano lemma** provides a lower bound on error probability:[32]

\[
P_e \geq \frac{1}{\log M + 1} \left( 1 - \frac{n I(\text{sample}; \text{hypothesis}) + \log 2}{n} \right)
\]

where $I(\cdot)$ is mutual information.

**SOC Implication:** The **number of alerts required** to reliably distinguish attack types is fundamentally limited by the mutual information between traffic features and attack labels. Systems achieving close to this Fano limit represent information-theoretically optimal alert prioritization.[32]

#### 10.2 The Channel Coding Theorem

By analogy with the **Channel Coding Theorem**, the **maximum reliable alert prioritization rate** (alerts correctly categorized per unit time) is limited by the **channel capacity:**[34]

\[
C = \max_{p(x)} I(X; Y)
\]

where $X$ is the input (observed traffic features) and $Y$ is the output (detected alert type). **Alert prioritization systems should operate near this capacity** to maximize throughput while maintaining accuracy.

***

### Conclusions and Practical Recommendations

1. **Implement Bayesian Posterior Updating:** Combine multiple alert signals using Bayes' rule to avoid double-counting and achieve adaptive risk scoring.[19][20][3]

2. **Adopt Neyman-Pearson Thresholding:** Set alert thresholds based on the likelihood ratio $\frac{f_1(m)}{f_0(m)}$ constrained to a pre-specified false alarm rate $\alpha$.[15][16][6]

3. **Leverage Information-Theoretic Metrics:** Use mutual information and KL divergence to automatically identify the most relevant features for attack detection and filter noise.[11][10]

4. **Apply Sparse Coding and Compression:** Reduce event stream dimensionality via compressive sensing, enabling efficient anomaly detection in high-volume environments.[23][22][21]

5. **Account for Adversarial Evasion:** Monitor equilibrium attack strategies from game-theoretic models; focus detection resources on regions where attackers would optimally concentrate distorted traffic.[7][6]

6. **Use Sequential Hypothesis Testing:** Implement SPRT-based stopping rules to minimize expected alert volume while maintaining statistical guarantees on error rates.[29][28]

7. **Monitor KL Divergence Dynamically:** Adjust detection thresholds based on the information-theoretic distance between current and baseline traffic distributions.[18][5][6]

These principles, grounded in Shannon information theory, statistical hypothesis testing, and game-theoretic analysis, provide a rigorous mathematical foundation for building next-generation SOC alert prioritization systems with optimal signal-to-noise ratio performance in intrusion detection.

***

**References**

 webIEEE 2024 predicative framework[1]
  - GAN cybersecurity[2]
  - Quantum Shannon information[35]
  - Bayesian attack detection[34]
  - Shannon entropy DDoS detection[8]
  - Bayesian networks SOC[3]
  - Shannon entropy implementation[9]
  - Adaptive alert prioritization[4]
  - SNR overview[30]
  - Shannon entropy domain names[31]
  - Entropy-based anomaly detection[33]
  - Bayesian networks incident prioritization[19]
  - Bayesian networks alert prioritization[20]
  - Compressive sensing WSN[21]
  - SVM compressed sampling[22]
  - Stacked sparse autoencoder IDS[24]
  - Compressed sensing anomaly detection[23]
  - Stacked sparse autoencoder improved Gaussian[25]
  - Game-theoretic cybersecurity[5]
  - Sparse coding DDoS detection[26]
  - Hybrid IDS sparse autoencoder[27]
  - Neyman-Pearson lemma anomaly detection[15]
  - Neyman-Pearson lemma decision theory[32]
  - Acoustic feature extractor Neyman-Pearson[17]
  - Evasion-aware Neyman-Pearson detectors[6]
  - Game-theoretic Neyman-Pearson detection[7]
  - KL divergence anomaly detection[18]
  - GLRT anomaly detection[36]
  - KL divergence data drift[37]
  - Neyman-Pearson lemma Wikipedia[16]
  - Likelihood ratio OOD detection[38]
  - Information theoretic stopping criteria[13]
  - Stopping rules mutual information[14]
  - Optimal stopping sequential hypothesis[28]
  - Mutual information feature selection IDS[10]
  - Stopping rules mutual information feature[11]
  - Fisher information loss[39]
  - Optimal stopping Wikipedia[40]
  - Feature grouping mutual information IDS[41]
  - Fisher information Wikipedia[42]
  - Optimal stopping applications[29]
  - Mutual information-based feature selection IDS[12]